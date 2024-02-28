package com.edirnegezgini.accommodationservice.repository;

import com.edirnegezgini.accommodationservice.entity.Accommodation;
import com.edirnegezgini.commonservice.entity.AccommodationCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, UUID> {
    Accommodation findByTitle(String title);

    List<Accommodation> findAllByCategory(AccommodationCategory category);
}
