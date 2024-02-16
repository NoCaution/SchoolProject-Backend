package com.edirnegezgini.userservice.controller;

import com.edirnegezgini.commonservice.entity.APIResponse;
import com.edirnegezgini.commonservice.entity.dto.UpdateUserDto;
import com.edirnegezgini.commonservice.service.CommonService;
import com.edirnegezgini.userservice.entity.dto.ChangePasswordDto;
import com.edirnegezgini.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private CommonService commonService;


    @GetMapping("/getAuthenticatedUser")
    public APIResponse getAuthenticatedUser(){
        return userService.getAuthenticatedUser();
    }

    @PutMapping("/updateAuthenticatedUser")
    public APIResponse updateAuthenticatedUser(@RequestBody UpdateUserDto updateUserDto) {
        UUID authenticatedUserId = UUID.fromString(commonService.getLoggedInUser().getUsername());

        if(Objects.equals(authenticatedUserId, updateUserDto.getId())){
            return userService.updateUser(updateUserDto);
        }

        return new APIResponse(
                HttpStatus.FORBIDDEN,
                "permission needed"
        );
    }

    @PutMapping("/changePassword")
    public APIResponse changePassword(@RequestBody ChangePasswordDto changePasswordDto){
        return userService.changePassword(changePasswordDto);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getUserById/{id}")
    public APIResponse getUserById(@PathVariable UUID id){
        return userService.getUserById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getUserByEmail/{email}")
    public APIResponse getUserByEmail(@PathVariable String email){
        return userService.getUserByEmail(email);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAll")
    public APIResponse getAll(){
        return userService.getAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateUser")
    public APIResponse updateUser(@RequestBody UpdateUserDto updateUserDto){
        return userService.updateUser(updateUserDto);
    }
}
