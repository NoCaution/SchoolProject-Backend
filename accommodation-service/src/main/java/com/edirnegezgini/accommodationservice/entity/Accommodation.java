package com.edirnegezgini.accommodationservice.entity;

import com.edirnegezgini.commonservice.entity.AccommodationCategory;
import com.edirnegezgini.commonservice.entity.BasePlace;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "accommodations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Accommodation extends BasePlace {
    private String title;

    private String image;

    @Column(length = 2048)
    private String info;

    private String location;

    @Enumerated(EnumType.STRING)
    private AccommodationCategory category;
}
