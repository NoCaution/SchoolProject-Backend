package com.edirnegezgini.userservice.entity;

import com.edirnegezgini.commonservice.entity.BaseModel;
import com.edirnegezgini.commonservice.entity.Role;
import com.edirnegezgini.commonservice.entity.dto.UpdateUserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User extends BaseModel implements UserDetails {

    private String name;

    private String lastName;

    private String email;

    private String password;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(UUID id, String name, String lastName, String email, String password, String phoneNumber, Role role, Date createdAt){
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.createdAt = createdAt;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority(role.toString()));
    }

    @Override
    public String getUsername() {
        return id.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User copyWith(UpdateUserDto dto) {
        return new User(
                this.id,
                Objects.equals(this.name, dto.getName()) ? this.name : dto.getName(),
                Objects.equals(this.lastName, dto.getLastName()) ? this.lastName : dto.getLastName(),
                Objects.equals(this.email, dto.getEmail()) ? this.email : dto.getEmail(),
                this.password,
                Objects.equals(this.phoneNumber, dto.getPhoneNumber()) ? this.phoneNumber : dto.getPhoneNumber(),
                this.role,
                this.createdAt
        );
    }
}
