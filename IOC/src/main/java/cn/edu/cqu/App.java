package cn.edu.cqu;


import cn.edu.cqu.utils.ClassTool;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public class App
{
    public static void main( String[] args )
    {
        //Set<Class<?>> s = ClassTool.getClasses(App.class);
        IOCcontainer ioc = new IOCcontainer(Boss.class);
        Usercontroller uc = ioc.getBean(Usercontroller.class);
        UserService us = ioc.getBean(UserService.class );
        String buffer=ioc.getBean(String.class);
        System.out.println(buffer);
//        Set<Class<?>> pk = ClassTool.getClasses("cn.edu.cqu");
//        for(Class<?> clz :pk ){
//            System.out.println(clz);
//        }
    }
}
