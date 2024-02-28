package com.edirnegezgini.favoriteservice.entity.dto;

import com.edirnegezgini.commonservice.entity.BasePlaceCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FavoriteDto {
    private UUID id;

    private UUID favoritePlaceId;

    private UUID userId;

    private BasePlaceCategory category;
}
