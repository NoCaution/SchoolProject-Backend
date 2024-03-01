package com.edirnegezgini.restaurantservice.config;

import com.edirnegezgini.commonservice.util.JWTUtil;
import com.edirnegezgini.commonservice.util.JwtToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonServiceConfig {
    @Bean
    public JWTUtil jwtUtil(){
        return new JWTUtil();
    }

    @Bean
    public JwtToken jwtToken(){
        return new JwtToken();
    }
}
