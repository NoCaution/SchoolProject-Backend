package com.edirnegezgini.accommodationservice.entity.dto;

import com.edirnegezgini.commonservice.entity.AccommodationCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccommodationDto {
    private String title;

    private String image;

    private String info;

    private String location;

    private AccommodationCategory category;
}
