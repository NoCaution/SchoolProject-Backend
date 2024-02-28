package com.edirnegezgini.commonservice.service;

import com.edirnegezgini.commonservice.entity.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class CommonService {

    public User getLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.isAuthenticated()){
            return (User) authentication.getPrincipal();
        }

        return null;
    }

    public boolean isAdminRequest(User authenticatedUser){
        String role = authenticatedUser.getAuthorities().stream().toList().get(0).getAuthority();
        return Role.valueOf(role) == Role.ADMIN;
    }

}
