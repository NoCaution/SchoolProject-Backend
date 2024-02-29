package com.edirnegezgini.visitationservice.entity;

import com.edirnegezgini.commonservice.entity.BasePlace;
import com.edirnegezgini.commonservice.entity.BasePlaceCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Visitation extends BasePlace {
    private UUID visitedPlaceId;

    @Enumerated(EnumType.STRING)
    private BasePlaceCategory category;

    private String note;
}
