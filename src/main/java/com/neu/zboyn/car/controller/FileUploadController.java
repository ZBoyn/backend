package com.neu.zboyn.car.controller;

import com.neu.zboyn.car.dto.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileUploadController {

    // 从配置文件 application.yml 读取文件存储路径
    @Value("${file.upload-dir}")
    private String uploadDir;

    // 从配置文件 application.yml 读取服务器访问地址
    @Value("${file.server-url}")
    private String serverUrl;

    @PostMapping("/upload")
    public Response<String> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Response.error(400, "上传失败，请选择文件", "file is empty");
        }

        // 获取原始文件名
        String originalFilename = file.getOriginalFilename();
        // 获取文件后缀
        String suffix = null;
        if (originalFilename != null) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        // 使用UUID生成新的文件名，防止重名
        String newFileName = UUID.randomUUID() + suffix;

        // 创建文件存储路径
        File dest = new File(new File(uploadDir).getAbsolutePath(), newFileName);

        // 确保目录存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        try {
            // 保存文件
            file.transferTo(dest);

            // 拼接可访问的URL并返回给前端
            String fileUrl = serverUrl + "/uploads/" + newFileName;

            return Response.success(fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return Response.error(500, "文件上传失败: " + e.getMessage(), "upload error");
        }
    }
}