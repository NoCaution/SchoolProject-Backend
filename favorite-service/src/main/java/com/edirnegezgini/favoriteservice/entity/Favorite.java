package com.edirnegezgini.favoriteservice.entity;

import com.edirnegezgini.commonservice.entity.BaseModel;
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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "favorites")
public class Favorite extends BaseModel {
    private UUID favoritePlaceId;

    private UUID userId;

    @Enumerated(EnumType.STRING)
    private BasePlaceCategory category;
}
