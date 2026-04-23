package com.lrm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// 经AI辅助生成：DeepSeek最新版, 2026-04-19
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 映射 /uploads/** 到项目根目录下的 static/uploads/
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:static/uploads/");
    }
}