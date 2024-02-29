package com.edirnegezgini.visitationservice.config;

import com.edirnegezgini.commonservice.client.RestClient;
import com.edirnegezgini.commonservice.service.CommonService;
import com.edirnegezgini.commonservice.util.CustomModelMapper;
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
    public CommonService commonService(){
        return new CommonService();
    }

    @Bean
    public RestClient restClient(){
        return new RestClient();
    }

    @Bean
    public JwtToken jwtToken(){
        return new JwtToken();
    }

    @Bean
    public CustomModelMapper customModelMapper(){
        return new CustomModelMapper();
    }
}
