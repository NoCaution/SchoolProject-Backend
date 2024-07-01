package com.edirnegezgini.placeservice.entity;

import com.edirnegezgini.commonservice.entity.BasePlace;
import com.edirnegezgini.commonservice.entity.PlaceCategory;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "places")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Place extends BasePlace {

    @Column(unique = true)
    private String title;

    @Column(length = 2048)
    private String info;

    private String location;
    private String image;

    @Enumerated(EnumType.STRING)
    private PlaceCategory category;


}
