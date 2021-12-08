package com.chuan.app;

import com.chuan.annotation.MyController;
import com.chuan.annotation.MyRequestMapping;
import com.chuan.annotation.MyRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MyController
@MyRequestMapping("/chuan")
public class ChuanController {
    @MyRequestMapping("/doChuan")
    public void test2(HttpServletRequest request, HttpServletResponse response, @MyRequestParam("pam") String pam){
        try {
            response.getWriter().println("doTest2 method success!"+pam);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @MyRequestMapping("/doChuan1")
    public void doChuan1(HttpServletRequest request, HttpServletResponse response, @MyRequestParam("pam") String pam,@MyRequestParam("b") String b){
        try {
            response.getWriter().println("doTest2 method success!pam:"+pam+",b:"+b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @MyRequestMapping("/doChuan2")
    public void doChuan2(HttpServletRequest request, HttpServletResponse response, @MyRequestParam("pam") String pam){
        try {
            response.getWriter().println("doTest2 method success!"+pam);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
