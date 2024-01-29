package com.edirnegezgini.authservice.repository;


import com.edirnegezgini.authservice.entity.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);
}
