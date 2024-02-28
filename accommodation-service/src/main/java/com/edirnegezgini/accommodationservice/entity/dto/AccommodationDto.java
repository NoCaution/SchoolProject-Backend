package com.edirnegezgini.accommodationservice.entity.dto;

import com.edirnegezgini.commonservice.entity.AccommodationCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationDto {
    private UUID id;

    private String title;

    private byte[] image;

    private String info;

    private String location;

    private AccommodationCategory category;
}
