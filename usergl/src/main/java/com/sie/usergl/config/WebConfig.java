package com.sie.usergl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                //.allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "OPTIONS", "PUT")
                .maxAge(3600)
                .allowCredentials(true);
    }
}
