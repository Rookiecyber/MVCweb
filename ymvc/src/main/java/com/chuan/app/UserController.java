package com.chuan.app;

import com.chuan.annotation.MyAutowired;
import com.chuan.annotation.MyController;
import com.chuan.annotation.MyRequestMapping;
import com.chuan.annotation.MyRequestParam;
import com.chuan.app.entity.User;
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
    UserServiceImpl userService;

    @MyRequestMapping("/find")
    public void find(HttpServletRequest request, HttpServletResponse response) throws Exception{
        User u = new User("test","zhangsan",20);

        String UserJson = JSON.toJSONString(u);
        response.getWriter().println(UserJson);
    }
    @MyRequestMapping("/findAll")
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws Exception{
//        List<User> users= userService.getAll();
        List<User> users = new ArrayList<User>();
        System.out.println(users.size());
        users.add( new User("1","zhangsan",20));
        users.add( new User("2","zhangsan",20));
        users.add( new User("3","zhangsan",20));
        String ListUserJson = JSON.toJSONString(users);
        response.getWriter().println(ListUserJson);
    }
    @MyRequestMapping("/add")
    public void add(@MyRequestParam("a") int a, @MyRequestParam("b")int b,HttpServletRequest request ,HttpServletResponse response) throws Exception{
        response.getWriter().println(a+b);
    }
}
