package com.edirnegezgini.commonservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.userdetails.User;

@Configuration
@EnableJpaAuditing
public class AuditConfig {

    @Bean
    public AuditorAware<User> auditorAware(){
        return new AuditorAwareImpl();
    }
}
