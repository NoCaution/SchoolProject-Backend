package com.edirnegezgini.commonservice.entity.dto;

import com.edirnegezgini.commonservice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDetailsDto {
    private UUID id;

    private String password;

    private Role role;
}
