package com.edirnegezgini.userservice.service;

import com.edirnegezgini.commonservice.entity.APIResponse;
import com.edirnegezgini.commonservice.entity.dto.UpdateUserDto;
import com.edirnegezgini.commonservice.entity.dto.UserDetailsDto;
import com.edirnegezgini.commonservice.entity.dto.UserDto;
import com.edirnegezgini.commonservice.service.CommonService;
import com.edirnegezgini.commonservice.util.CustomModelMapper;
import com.edirnegezgini.userservice.entity.User;
import com.edirnegezgini.userservice.entity.dto.ChangePasswordDto;
import com.edirnegezgini.userservice.repository.UserRepository;
import com.google.common.base.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private CustomModelMapper customModelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public APIResponse getAuthenticatedUser() {
        org.springframework.security.core.userdetails.User userDetails = commonService.getLoggedInUser();

        if (userDetails == null) {
            return new APIResponse(
                    HttpStatus.UNAUTHORIZED,
                    "authentication required"
            );
        }

        UUID id = UUID.fromString(userDetails.getUsername());
        User loggedInUser = getById(id);

        if (loggedInUser == null) {
            return new APIResponse(
                    HttpStatus.NOT_FOUND,
                    "user not found"
            );
        }

        UserDetailsDto userDto = customModelMapper.map(loggedInUser, UserDetailsDto.class);

        return new APIResponse(
                HttpStatus.OK,
                "success",
                userDto
        );
    }

    public APIResponse getUserById(UUID id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return new APIResponse(
                    HttpStatus.NOT_FOUND,
                    "no user found"
            );
        }

        UserDto userDto = customModelMapper.map(user, UserDto.class);

        return new APIResponse(
                HttpStatus.OK,
                "success",
                userDto
        );
    }

    public APIResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return new APIResponse(
                    HttpStatus.NOT_FOUND,
                    "no user found"
            );
        }

        UserDto userDto = customModelMapper.map(user, UserDto.class);

        return new APIResponse(
                HttpStatus.OK,
                "success",
                userDto
        );
    }

    public APIResponse getAll() {
        List<User> userList = userRepository.findAll();

        if (userList.isEmpty()) {
            return new APIResponse(
                    HttpStatus.OK,
                    "success",
                    userList
            );
        }

        List<UserDto> userDtoList = customModelMapper.convertList(userList, UserDto.class);

        return new APIResponse(
                HttpStatus.OK,
                "success",
                userDtoList
        );
    }

    public APIResponse updateUser(UpdateUserDto updateUserDto) {
        if (updateUserDto.getId() == null) {
            return new APIResponse(
                    HttpStatus.BAD_REQUEST,
                    "user id can not be null"
            );
        }

        User user = userRepository.findById(updateUserDto.getId()).orElse(null);

        if (user == null) {
            return new APIResponse(
                    HttpStatus.NOT_FOUND,
                    "no user found"
            );
        }

        User newUser = user.copyWith(updateUserDto);

        userRepository.save(newUser);

        return new APIResponse(
                HttpStatus.OK,
                "success"
        );
    }

    public APIResponse changePassword(ChangePasswordDto changePasswordDto) {
        UUID authenticatedUserId = UUID.fromString(commonService.getLoggedInUser().getUsername());

        if (!Objects.equal(changePasswordDto.getId(), authenticatedUserId)) {
            return new APIResponse(
                    HttpStatus.FORBIDDEN,
                    "permission needed"
            );
        }

        User authenticatedUser = userRepository.findById(authenticatedUserId).orElse(null);

        if (authenticatedUser == null) {
            return new APIResponse(
                    HttpStatus.UNAUTHORIZED,
                    "authentication required"
            );
        }

        boolean isPasswordNotMatch = !passwordEncoder.matches(changePasswordDto.getOldPassword(), authenticatedUser.getPassword());

        if (isPasswordNotMatch) {
            return new APIResponse(
                    HttpStatus.UNAUTHORIZED,
                    "password incorrect"
            );
        }

        String newEncodedPassword = passwordEncoder.encode(changePasswordDto.getNewPassword());
        authenticatedUser.setPassword(newEncodedPassword);

        userRepository.save(authenticatedUser);

        return new APIResponse(
                HttpStatus.OK,
                "success"
        );
    }

    private User getById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }


    @Override
    public org.springframework.security.core.userdetails.User loadUserByUsername(String username) throws UsernameNotFoundException {
        UUID id = UUID.fromString(username);
        User user = userRepository.findById(id).orElse(new User());

        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(),
                Set.of(new SimpleGrantedAuthority(user.getRole().toString()))
        );
    }
}
