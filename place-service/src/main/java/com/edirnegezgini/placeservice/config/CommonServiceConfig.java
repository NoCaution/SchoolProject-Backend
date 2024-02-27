package com.edirnegezgini.placeservice.config;

import com.edirnegezgini.commonservice.util.CustomModelMapper;
import com.edirnegezgini.commonservice.util.JWTUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonServiceConfig {
    @Bean
    JWTUtil jwtUtil(){
        return new JWTUtil();
    }

    @Bean
    CustomModelMapper customModelMapper(){
        return new CustomModelMapper();
    }

}
