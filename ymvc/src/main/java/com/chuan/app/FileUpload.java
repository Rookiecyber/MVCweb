package com.chuan.app;

import com.chuan.annotation.MyController;
import com.chuan.annotation.MyRequestMapping;
import com.chuan.annotation.MyRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.fileupload.FileItem;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
@MyController
@MyRequestMapping(value = "/file")
public class FileUpload {
    @MyRequestMapping("/upload")
    public void upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 创建磁盘工厂 缓冲区和磁盘目录
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置文件上传的大小限制
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(1024 * 1024 * 10);// 最大上传10M
        upload.setHeaderEncoding("utf-8");// 设置编码格式
        List<FileItem> files = upload.parseRequest(request);
        for(FileItem fileItem :files){
            //获取上传的文件名
            String name = fileItem.getName();
            String path="E:\\JavaCode\\MVCweb\\fileupload\\";//假设上传路径固定
            // 定义一个新的文件来接收
            File newfile = new File(path + name);
            // 从缓冲区写入磁盘
            fileItem.write(newfile);
            fileItem.delete();
        }
        response.getWriter().println("File upload Success!! ");
    }
}
