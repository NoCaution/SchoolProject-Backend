package com.edirnegezgini.authservice.service;

import com.edirnegezgini.authservice.entity.dto.AuthenticationRequestDto;
import com.edirnegezgini.authservice.entity.dto.RegistrationRequestDto;
import com.edirnegezgini.authservice.repository.AuthRepository;
import com.edirnegezgini.commonservice.entity.APIResponse;
import com.edirnegezgini.authservice.entity.dto.User;
import com.edirnegezgini.commonservice.util.CustomModelMapper;
import com.edirnegezgini.commonservice.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomModelMapper customModelMapper;

    public APIResponse login(AuthenticationRequestDto authenticationRequestDto) {
        User user = authRepository.findByEmail(authenticationRequestDto.getEmail());

        if (user == null) {
            return new APIResponse(
                    HttpStatus.NOT_FOUND,
                    "no user found"
            );
        }

        boolean isPasswordCorrect = passwordEncoder.matches(authenticationRequestDto.getPassword(), user.getPassword());

        if (!isPasswordCorrect) {
            return new APIResponse(
                    HttpStatus.UNAUTHORIZED,
                    "password incorrect"
            );
        }

        org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );

        String token = jwtUtil.generateJwtToken(userDetails);

        if (token == null) {
            return new APIResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "error while creating token"
            );
        }

        return new APIResponse(
                HttpStatus.OK,
                "success",
                token
        );
    }

    public APIResponse register(RegistrationRequestDto registrationRequestDto) {
        boolean isNotUniqueUser = !isUniqueUser(registrationRequestDto.getEmail());

        if (isNotUniqueUser) {
            return new APIResponse(
                    HttpStatus.BAD_REQUEST,
                    "this email is linked to another account"
            );
        }

        //encode password
        String encodedPassword = passwordEncoder.encode(registrationRequestDto.getPassword());
        registrationRequestDto.setPassword(encodedPassword);

        //create user
        User user = customModelMapper.map(registrationRequestDto, User.class);
        String fullName = String.join(" ", registrationRequestDto.getName(), registrationRequestDto.getLastName());
        user.setFullName(fullName);

        //save user
        User createdUser = authRepository.save(user);

        org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(
                createdUser.getUsername(),
                createdUser.getPassword(),
                createdUser.getAuthorities()
        );

        //create token

        String token = jwtUtil.generateJwtToken(userDetails);

        if (token == null) {
            return new APIResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "error while creating token"
            );
        }

        return new APIResponse(
                HttpStatus.CREATED,
                "success",
                token
        );
    }

    private boolean isUniqueUser(String email) {
        return authRepository.findByEmail(email) == null;
    }
}
