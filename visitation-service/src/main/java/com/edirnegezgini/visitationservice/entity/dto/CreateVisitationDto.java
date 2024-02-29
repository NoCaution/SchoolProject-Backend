package com.edirnegezgini.visitationservice.entity.dto;

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
public class CreateVisitationDto {
    private UUID visitedPlaceId;

    private BasePlaceCategory category;

    private String note;
}
