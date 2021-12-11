package com.chuan.servlet;

import com.chuan.annotation.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;

public class MyDispatcherServlet extends HttpServlet {
    //将后缀为properties的文件转化为properties对象
    private Properties properties=new Properties();

    private List<String> classNames = new ArrayList<String>();

    private Map<String, Object> IOCObject = new HashMap();

    private Map<String, Method> handlerMapping = new  HashMap();

    private Map<String, Object> handlerController  =new HashMap();



    @Override
    public void init(ServletConfig config) throws ServletException{
        //将main.properties文件加载进来
        loadProperties(config.getInitParameter("config"));
        //扫描properties文件里指定的路径
        doScanner(properties.getProperty("scanPackage"));
        //将包含controller注解的类的对象放到ioc容器中
        doPutIoc();
        //映射url
        //依赖注入
        doAutowired();
        getMapping();
        System.out.println("init完毕！");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    private void loadProperties(String location){
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(location);
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void doScanner(String packageName){
        URL url = this.getClass().getClassLoader().getResource("/" + packageName.replaceAll("\\.", "/"));
        File file =new File(url.getFile());
        for (File curFile:file.listFiles()){
            if (curFile.isDirectory()){
                doScanner(packageName+"."+curFile.getName());
            }else {
                String fileNmae=packageName+"."+curFile.getName().replace(".class","");
                classNames.add(fileNmae);
            }
        }

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //处理请求
            doDispatch(req,resp);
        } catch (Exception e) {
            resp.getWriter().write("500!! Server Exception");
        }
    }
    /**
     * 把字符串的首字母小写
     */
    private String toLowerFirstWord(String name){
        char[] charArray = name.toCharArray();
        charArray[0] += 32;
        return String.valueOf(charArray);
    }
    private void  doPutIoc(){
        if (classNames.isEmpty()){
            return;
        }
        for (String className:classNames){
            try {
                // 读取类,通过反射来实例化
                Class<?> clazz= Class.forName(className);
                if (clazz.isAnnotationPresent(MyController.class)){
                    IOCObject.put(toLowerFirstWord(clazz.getSimpleName()),clazz.newInstance());
                }else if(clazz.isAnnotationPresent(MyService.class)){
                    MyService annotation = clazz.getAnnotation(MyService.class);
                    String beanName = annotation.value();
                    Object beanInstance = clazz.newInstance();
                    if (Objects.equals("", beanName)) {
                        // 多个接口
                        Class<?>[] interfaces = clazz.getInterfaces();
                        for(Class<?> c1 : interfaces){
                            String classSimpleName = toLowerFirstWord(c1.getSimpleName());
                            IOCObject.put(classSimpleName, beanInstance);
                        }
                    }else {
                        IOCObject.put(beanName,beanInstance);
                    }
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("类未找到！");
            }  catch (InstantiationException e) {
                throw new RuntimeException("不能实例化！");
            } catch (IllegalAccessException e) {
                throw new RuntimeException("无权限操作！");
            }
        }
    }
    public void doAutowired(){
        if (IOCObject.size() ==0 ){
            return;
        }
        Iterator<Map.Entry<String, Object>> iocIter = IOCObject.entrySet().iterator();
        while (iocIter.hasNext()){
            //  /*classSimpleName*/, Object /*Class instance*/
            Map.Entry<String, Object> entry = iocIter.next();
            String classSimpleName = entry.getKey();
            Object beanInstance = entry.getValue();
            //  目前实现成员变量的依赖注入
            // 后续处理构造器和方法
            Field[] declaredFields = beanInstance.getClass().getDeclaredFields();
            for (Field declaredField: declaredFields) {
                if (declaredField.isAnnotationPresent(MyAutowired.class)){
                    declaredField.setAccessible(true);
                    MyAutowired myEasyAutowired = declaredField.getAnnotation(MyAutowired.class);
                    String autowiredBeanName = myEasyAutowired.value();
                    if (Objects.equals("",autowiredBeanName) || Objects.equals(null, autowiredBeanName)){
                        Class<?> declaredFieldType = declaredField.getType();
                        autowiredBeanName = toLowerFirstWord(declaredFieldType.getSimpleName());
                    }
                    try {
                        Object Service = declaredField.get(beanInstance);
                        declaredField.setAccessible(true);
                        declaredField.set(beanInstance, IOCObject.get(autowiredBeanName));
                        Service = declaredField.get(beanInstance);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public void getMapping(){
        if (IOCObject.isEmpty()||IOCObject.size()==0){
            return;
        }
        for (Entry<String,Object> entry:IOCObject.entrySet()){
            Class<?> clazz=entry.getValue().getClass();
            if (!clazz.isAnnotationPresent(MyController.class)){
                continue;
            }
            if (!clazz.isAnnotationPresent(MyRequestMapping.class)){
                continue;
            }
            //获取类上的路径
            String baseUrl = clazz.getAnnotation(MyRequestMapping.class).value();
            for (Method method:clazz.getMethods()){
                if (!method.isAnnotationPresent(MyRequestMapping.class)){
                    continue;
                }
                String url = baseUrl+ method.getAnnotation(MyRequestMapping.class).value();
                url=url.replaceAll("/+","/");
                try {
                    handlerMapping.put(url,method);
                    // 优化了重复创建handlercontroller实例
                    Object obj=null;
                    String objName=toLowerFirstWord(clazz.getSimpleName());
                    //如果已经存在，则复用同一个对象
                    if (IOCObject.containsKey(objName)){
                        obj =IOCObject.get(objName);
                    }else {
                        obj=clazz.newInstance();
                    }
                    handlerController.put(url,obj);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        if (handlerMapping.isEmpty()||handlerMapping.size()==0){
            resp.setCharacterEncoding("UTF-8");
            resp.setHeader("Content-Type", "text/html;charset=UTF-8");
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write("404 页面未找到");
            return;
        }
        String uri =  req.getRequestURI();
        String contextPath = req.getContextPath();
        String url = uri.replace(contextPath,"");
        if (!handlerMapping.containsKey(url)){
            resp.setCharacterEncoding("UTF-8");
            resp.setHeader("Content-Type", "text/html;charset=UTF-8");
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write("404 页面未找到");
            return;
        }
        Method method = handlerMapping.get(url);
        Class<?>[] parameterTypes = method.getParameterTypes();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        List<String> requestParamList=new ArrayList<>();
        int j=0;
        for (Annotation[] annotations : parameterAnnotations) {
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> aClass = annotation.annotationType();
                if (aClass.getName().equals("com.chuan.annotation.MyRequestParam")){
                    //获取注解的value
                    String paramName =  ((MyRequestParam)annotation).value();
                    requestParamList.add(paramName);
                }
            }
        }
        Object[] paramValues = new Object[parameterTypes.length];
        //获取请求参数
        Map<String, String[]> parameterMap = req.getParameterMap();
        for (int i=0;i<paramValues.length;i++){
            //获取参数类型
            String simpleName = parameterTypes[i].getSimpleName();
            if (simpleName.equals("HttpServletRequest")){
                paramValues[i]=req;
                continue;
            }else if (simpleName.equals("HttpServletResponse")){
                paramValues[i]=resp;
                continue;
            }else {
                for (Entry<String,String[]> entry:parameterMap.entrySet()){
                    if (requestParamList.get(j).equals(entry.getKey())) {
                        //相同的参数只取第一个的值
                        paramValues[i]=entry.getValue()[0];
                        continue;
                    }
                }
                j++;
            }
        }
        method.invoke(handlerController.get(url),paramValues);
    }
}
