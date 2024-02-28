package com.edirnegezgini.favoriteservice.config;

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
    JWTUtil jwtUtil(){
        return new JWTUtil();
    }

    @Bean
    CustomModelMapper customModelMapper(){
        return new CustomModelMapper();
    }

    @Bean
    RestClient restClient(){
        return new RestClient();
    }

    @Bean
    CommonService commonService(){
        return new CommonService();
    }

    @Bean
    JwtToken jwtToken(){
        return new JwtToken();
    }
}
