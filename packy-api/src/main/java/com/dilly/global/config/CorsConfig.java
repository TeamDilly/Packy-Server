package com.dilly.global.config;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Profile({"dev", "prod"})
public class CorsConfig implements WebMvcConfigurer {

    @Value("${cors.allowedOrigins}")
    private List<String> allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedHeaders("*")
            .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
            .allowedOrigins(allowedOrigins.toArray(new String[0]))
            .maxAge(3600);
    }
}
