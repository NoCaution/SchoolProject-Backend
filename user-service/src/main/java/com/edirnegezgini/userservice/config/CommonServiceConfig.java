package com.edirnegezgini.userservice.config;

import com.edirnegezgini.commonservice.service.CommonService;
import com.edirnegezgini.commonservice.util.CustomModelMapper;
import com.edirnegezgini.commonservice.util.JWTUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonServiceConfig {
    @Bean
    CustomModelMapper customModelMapper(){
        return new CustomModelMapper();
    }

    @Bean
    JWTUtil jwtUtil(){
        return new JWTUtil();
    }

    @Bean
    CommonService commonService(){
        return new CommonService();
    }
}
