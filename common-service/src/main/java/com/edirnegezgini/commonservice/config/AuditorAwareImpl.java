package com.edirnegezgini.commonservice.config;

import com.edirnegezgini.commonservice.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<User> {
    @Autowired
    private CommonService commonService;

    @NonNull
    @Override
    public Optional<User> getCurrentAuditor() {
        User user = commonService.getLoggedInUser();
        return Optional.of(user);
    }
}
