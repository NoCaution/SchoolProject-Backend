package com.edirnegezgini.placeservice.entity.dto;

import com.edirnegezgini.commonservice.entity.PlaceCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDto {
    private UUID id;

    private String title;

    private String info;

    private String location;

    private byte[] image;

    private PlaceCategory category;
}
