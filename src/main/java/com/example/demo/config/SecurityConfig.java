package com.example.demo.config;

import com.example.demo.Service.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMapping;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(request -> {
                    var config = new org.springframework.web.cors.CorsConfiguration();
                    config.addAllowedOrigin("http://localhost:3000"); // Frontend adresini buraya ekledim
                    config.addAllowedMethod("*"); // Her tür HTTP metoduna izin ver
                    config.addAllowedHeader("*"); // Her tür header'a izin ver
                    config.setAllowCredentials(true);
                    return config;
                }))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/signup/**","/login/**","/api/auth/**","/error").permitAll()
                        .requestMatchers("/mainPage/**").authenticated()// kayıt isteğine izin ver
                        .requestMatchers("/organizatorMainPage/**").authenticated()
                        .requestMatchers("/Profile/**").authenticated()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/adminMainPage/**").hasAuthority("ADMIN")
                        .requestMatchers("/sehir/**").permitAll()
                        .requestMatchers("/veri/**").permitAll()
                        .requestMatchers("/etkinlikler/kapak-foto/**").permitAll()
                        .requestMatchers("/error/*").permitAll()
                        .requestMatchers("/api/organizator/**").hasAuthority("ORG")//bunu ekledim
                        .requestMatchers("/biletAl/**").hasAuthority("ROLE_USER")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Stateless session
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)  // JWT filtreleme
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}