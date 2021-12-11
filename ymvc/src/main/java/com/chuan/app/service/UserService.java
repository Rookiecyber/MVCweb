package com.chuan.app.service;


import com.chuan.app.entity.User;

import java.util.List;

public interface UserService {
    public User findByID(String ID);
    public void insert(String id,String name,Integer age);
    public void delete(String id);
    public List<User> getAll();
}
