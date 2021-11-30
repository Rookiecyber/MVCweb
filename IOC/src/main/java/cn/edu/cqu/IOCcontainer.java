package cn.edu.cqu;

import cn.edu.cqu.anotation.*;
import cn.edu.cqu.utils.ClassTool;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class IOCcontainer {
    private Map<Class<?>, Object> map = new HashMap<>();

    public IOCcontainer(Class<?>... clzs){
        try {
            for (Class<?> clz : clzs) {
                System.out.println(clz);
                loadBeans(clz);
                scanComponents(clz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void scanComponents(Class<?> clz) {
        ComponentScan cs=clz.getAnnotation(ComponentScan.class);
        if(cs!=null)
        {
            String[] packages=cs.value();
            if(packages.length==0)
                packages=new String[] {clz.getPackage().getName()};
            for(String pk:packages)
            {
                Set<Class<?>> clzSet= ClassTool.getClasses(pk);
                for(Class<?> c:clzSet)
                {
                    Component com=c.getAnnotation(Component.class);
                    if(com!=null)
                    {
                        loadComponent(c);
                    }
                }
            }
        }
    }

    private void loadComponent(Class<?> c) {
        try {
            Object o=c.newInstance();
            Field[] fields=c.getDeclaredFields();
            for(Field f:fields)
            {
                Autowired aw=f.getAnnotation(Autowired.class);
                if(aw!=null)
                {
                    f.setAccessible(true);
                    f.set(o, map.get(f.getType()));
                }
            }
            map.put(c, o);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadBeans(Class<?> clz) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Configuration conf = clz.getAnnotation(Configuration.class);
        if (conf != null) {
            Object confObj = clz.newInstance();
            Method[] methods = clz.getDeclaredMethods();
            for (Method m : methods) {
                Bean bean = m.getAnnotation(Bean.class);
                if (bean != null) {
                    Object ret = m.invoke(confObj);
                    map.put(m.getReturnType(), ret);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clz) {
        return (T) map.get(clz);
    }
}
//
//import cn.edu.cqu.anotation.Autowired;
//import cn.edu.cqu.anotation.Component;
//import lombok.extern.java.Log;
//import lombok.extern.slf4j.Slf4j;
//
//import java.lang.reflect.Field;
//import java.util.HashMap;
//import java.util.Map;
//@Slf4j
//public class IOCcontainer{
//    /**
//     *存放所有的bean
//     * */
//    public Map<String, Object> beanMap = new HashMap<>();
//    /**
//     * 通过name获取 Bean 实例
//     */
//    public Object getBeanByName(String name) {
//        if (null == name) {
//            return null;
//        }
//        return beanMap.get(name);
//    }
//    /**
//     * 通过Class获取 Bean 实例
//     */
//    public Object getBean(Class<?> clz) {
//        String name = clz.getName();
//        Object object = beanMap.get(name);
//        if(null != object){
//            return  object;
//        }
//        return null;
//    }
//    public  IOCcontainer(){
//        addAnnotatedClass(UserService.class);
//        addAnnotatedClass(Usercontroller.class);
//    }
//    /**
//    * 注入对象
//    * */
//    public void addAnnotatedClass(Class<?> clz)
//    {
//        Component component=clz.getAnnotation(Component.class);
//        try {
//            Object obj=clz.newInstance();
//            beanMap.put(component.name(), obj);
//            Field[] fields=clz.getDeclaredFields();
//            for(Field f:fields)
//            {
//                Autowired autowired=f.getAnnotation(Autowired.class);
//                if(autowired!=null)
//                {
//                    f.setAccessible(true);
//                    f.set(obj, beanMap.get(autowired.name()));
//                }
//            }
//        } catch (InstantiationException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }
//}
