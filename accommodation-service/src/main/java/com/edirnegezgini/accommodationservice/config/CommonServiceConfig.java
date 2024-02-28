package com.edirnegezgini.accommodationservice.config;

import com.edirnegezgini.commonservice.service.CommonService;
import com.edirnegezgini.commonservice.util.CustomModelMapper;
import com.edirnegezgini.commonservice.util.JWTUtil;
import com.edirnegezgini.commonservice.util.JwtToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonServiceConfig {
    @Bean
    public CustomModelMapper customModelMapper(){
        return new CustomModelMapper();
    }

    @Bean
    public JWTUtil JWTUtil(){
        return new JWTUtil();
    }

    @Bean
    public CommonService commonService(){
        return new CommonService();
    }

    @Bean
    public JwtToken jwtToken(){
        return new JwtToken();
    }
}
