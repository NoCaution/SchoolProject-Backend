package com.edirnegezgini.placeservice.repository;

import com.edirnegezgini.placeservice.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlaceRepository  extends JpaRepository<Place, UUID> {
    Place findByTitle(String title);
}
