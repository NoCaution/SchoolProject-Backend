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
public class VisitationDto {
    private UUID id;

    private UUID visitedPlaceId;

    private UUID userId;

    private BasePlaceCategory category;

    private String note;
}
