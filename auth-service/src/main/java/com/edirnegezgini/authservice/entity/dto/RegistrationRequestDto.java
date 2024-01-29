package com.edirnegezgini.authservice.entity.dto;

import com.edirnegezgini.commonservice.entity.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequestDto {
    private String name;

    private String lastName;

    private String email;

    private String password;

    private String phoneNumber;

    private Role role = Role.USER;
}
