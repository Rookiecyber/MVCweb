package com.chuan.app;

import com.chuan.annotation.MyController;
import com.chuan.annotation.MyRequestMapping;
import com.chuan.annotation.MyRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MyController
@MyRequestMapping("/test")
public class TestController {

    @MyRequestMapping("/doTest2")
    public void test2(HttpServletRequest request, HttpServletResponse response){
        try {
            response.getWriter().println("doTest2 method success!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @MyRequestMapping("/login")
    public void lodin(HttpServletRequest request, HttpServletResponse response,
                      @MyRequestParam("username") String username,@MyRequestParam("password") String password){
        try {
            response.getWriter().println(username);
            response.getWriter().println(password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @MyRequestMapping("/upload")
    public void doChuan2(HttpServletRequest request, HttpServletResponse response, @MyRequestParam("file") String pam){
        try {
//            response.getWriter().println("upload success!"+pam);
            response.sendRedirect("http://localhost:8080/index.jsp");
//            return "index.jsp";
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return "index.jsp";
    }

}
