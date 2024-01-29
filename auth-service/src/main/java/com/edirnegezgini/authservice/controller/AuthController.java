package com.edirnegezgini.authservice.controller;

import com.edirnegezgini.authservice.entity.dto.AuthenticationRequestDto;
import com.edirnegezgini.authservice.entity.dto.RegistrationRequestDto;
import com.edirnegezgini.authservice.service.AuthService;
import com.edirnegezgini.commonservice.entity.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public APIResponse login(@RequestBody AuthenticationRequestDto authenticationRequestDto) {
        return authService.login(authenticationRequestDto);
    }

    @PostMapping("/register")
    public APIResponse register(@RequestBody RegistrationRequestDto registrationRequestDto) {
        return authService.register(registrationRequestDto);
    }
}
