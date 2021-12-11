package com.chuan.app.entity;

public class User {
    private String userId;
    private String name;
    private Integer age;
    public User(){}
    public User(String userId,String name,Integer age){
        this.userId = userId;
        this.name = name;
        this.age = age;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
}
