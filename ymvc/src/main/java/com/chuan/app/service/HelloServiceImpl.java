package com.chuan.app.service;

import com.chuan.annotation.MyService;

@MyService
public class HelloServiceImpl implements HelloService{
    @Override
    public void sayHi(String name) {
        System.out.println("Hello "+name);
    }

}
