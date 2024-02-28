package com.edirnegezgini.favoriteservice.repository;

import com.edirnegezgini.favoriteservice.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {
    List<Favorite> findAllByUserId(UUID userId);
}
