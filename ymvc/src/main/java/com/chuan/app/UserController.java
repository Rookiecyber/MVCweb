package com.chuan.app;

import com.chuan.annotation.*;
import com.chuan.app.entity.User;
import com.chuan.app.service.HelloService;
import com.chuan.app.service.UserService;
import com.chuan.app.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

@MyController
@MyRequestMapping("/user")
public class UserController {
    @MyAutowired
    UserService userService;

    @MyAutowired
    HelloService helloService;
    @MyRequestMapping("/say")
    public void say(HttpServletRequest request, HttpServletResponse response) throws Exception{
        helloService.sayHi("zhangsan");
        response.getWriter().println("UserJson");
    }
    @MyRequestMapping("/findById")
    public void find(HttpServletRequest request, HttpServletResponse response,
        @MyRequestParam("id") String id ) throws Exception{
        User user =  userService.findByID(id);
        String UserJson = JSON.toJSONString(user);
        response.getWriter().println(UserJson);
    }
    @MyRequestMapping("/add")
    public void add(HttpServletRequest request, HttpServletResponse response,
                     @MyRequestParam("id") String id,@MyRequestParam("name") String name,@MyRequestParam("age") String age ) throws Exception{
        userService.insert(id,name,Integer.valueOf(age).intValue());
        response.getWriter().println("Insert success!");
    }
    @MyRequestMapping("/delete")
    public void delete(HttpServletRequest request, HttpServletResponse response,
                    @MyRequestParam("id") String id ) throws Exception{
        userService.delete(id);
        response.getWriter().println("Delete success!");
    }
    @MyRequestMapping("/findAll")
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws Exception{
        List<User> users= userService.getAll();
        String ListUserJson = JSON.toJSONString(users);
        response.getWriter().println(ListUserJson);
    }
}
