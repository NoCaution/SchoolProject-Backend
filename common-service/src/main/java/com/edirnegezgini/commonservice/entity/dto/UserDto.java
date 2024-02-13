package com.edirnegezgini.commonservice.entity.dto;

import com.edirnegezgini.commonservice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private UUID id;

    private String name;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Role role;
}
