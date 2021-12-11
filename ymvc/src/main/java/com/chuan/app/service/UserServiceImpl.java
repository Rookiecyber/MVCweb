package com.chuan.app.service;

import com.chuan.annotation.MyService;
import com.chuan.app.entity.User;

import java.util.ArrayList;
import java.util.List;

@MyService
public class UserServiceImpl implements UserService{
    private static List<User> users=new ArrayList<User>();
    static {
        addUser("1","zhangsan",20);
        addUser("2","lisi",19);
        addUser("3","wangwu",21);
    }
    //静态方法模拟数据库
    public static void addUser(String id,String name,Integer age)
    {
        User u=new User();
        u.setAge(age);
        u.setUserId(id);
        u.setName(name);
        users.add(u);
    }
    @Override
    public void insert(String id,String name,Integer age){
        User u=new User();
        u.setAge(age);
        u.setUserId(id);
        u.setName(name);
        users.add(u);
    }
    @Override
    public void delete(String id){
        for(User u:users){
            if(u.getUserId().equals(id)){
                users.remove(u);
            }
        }
    }
    @Override
    public User findByID(String ID){
        for(User u:users){
            if(u.getUserId().equals(ID)){
                return u;
            }
        }
        return null;
    }
    @Override
    public List<User> getAll()
    {
        return  users;
    }
}
