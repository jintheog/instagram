package com.example.instagram.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        
        // 파일위치 절대경로화
        String uploadPath = Paths.get(uploadDir).toAbsolutePath().toUri().toString();

        // 어떤 요청이 어떤 폴더에 연결될지 설정
        registry.addResourceHandler("/uploads/**").addResourceLocations(uploadPath);
    }
}
