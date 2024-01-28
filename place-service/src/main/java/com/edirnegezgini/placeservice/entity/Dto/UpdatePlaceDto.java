package com.edirnegezgini.placeservice.entity.Dto;

import com.edirnegezgini.commonservice.entity.Enum.PlaceCategory;
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

    private byte[] image;

    private PlaceCategory category;
}
