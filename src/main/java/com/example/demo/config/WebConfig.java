package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Tüm endpoint'ler için CORS
                .allowedOrigins("http://localhost:3000") // Frontend'in çalıştığı port
                .allowedMethods("GET", "POST", "PUT", "DELETE") // İzin verilen HTTP metotları
                .allowedHeaders("*") // Herhangi bir başlık (header) kabul edilir
                .allowCredentials(true); // Eğer JWT kullanıyorsanız bu ayar gereklidir
    }
}
