package com.edirnegezgini.userservice.service;

import com.edirnegezgini.commonservice.entity.APIResponse;
import com.edirnegezgini.commonservice.entity.Role;
import com.edirnegezgini.commonservice.entity.dto.UserDetailsDto;
import com.edirnegezgini.commonservice.service.CommonService;
import com.edirnegezgini.userservice.entity.User;
import com.edirnegezgini.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommonService commonService;



    public APIResponse getAuthenticatedUser() {
        org.springframework.security.core.userdetails.User userDetails = commonService.getLoggedInUser();

        if (userDetails == null) {
            return new APIResponse(
                    HttpStatus.UNAUTHORIZED,
                    "authentication required"
            );
        }

        UUID id = UUID.fromString(userDetails.getUsername());
        String password = userDetails.getPassword();
        Role role = Role.valueOf(userDetails.getAuthorities().stream().toList().get(0).getAuthority());

        UserDetailsDto userDetailsDto = new UserDetailsDto(
                id,
                password,
                role
        );

        return new APIResponse(
                HttpStatus.OK,
                "success",
                userDetailsDto
        );
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
