package com.edirnegezgini.visitationservice.repository;

import com.edirnegezgini.commonservice.entity.BasePlaceCategory;
import com.edirnegezgini.visitationservice.entity.Visitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VisitationRepository extends JpaRepository<Visitation, UUID> {
    List<Visitation> findAllByCategory(BasePlaceCategory category);

    List<Visitation> findAllByCategoryAndUserId(BasePlaceCategory category, UUID userId);
}
