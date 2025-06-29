package com.neu.zboyn.car.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

//    @Value("${file.upload-dir}")
//    private String uploadDir;
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // 将相对路径转换为绝对路径
//        String absolutePath = new File(uploadDir).getAbsolutePath();
//        System.out.println(absolutePath);
//
//        // 处理 /uploads/** 路径（数据库中的路径）
//        registry.addResourceHandler("/uploads/**")
//                .addResourceLocations("file:" + absolutePath + "/");
//
//        // 处理 /uploads/uploads/** 路径（前端请求的路径）
//        registry.addResourceHandler("/uploads/uploads/**")
//                .addResourceLocations("file:" + absolutePath + "/");
//    }
}
