package com.edirnegezgini.placeservice.entity.dto;

import com.edirnegezgini.commonservice.entity.PlaceCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdatePlaceDto {
    private UUID id;

    private String title;

    private String info;

    private String location;

    private String image;

    private PlaceCategory category;
}
