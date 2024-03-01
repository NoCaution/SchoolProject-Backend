package com.edirnegezgini.restaurantservice.repository;

import com.edirnegezgini.restaurantservice.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {
    Restaurant findByTitle(String title);
}
