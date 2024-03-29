package com.edirnegezgini.placeservice.entity;

import com.edirnegezgini.commonservice.entity.BasePlace;
import com.edirnegezgini.commonservice.entity.PlaceCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "places")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Place extends BasePlace {
    private String title;

    private String info;

    private String location;

    @Lob
    private byte[] image;

    @Enumerated(EnumType.STRING)
    private PlaceCategory category;


}
