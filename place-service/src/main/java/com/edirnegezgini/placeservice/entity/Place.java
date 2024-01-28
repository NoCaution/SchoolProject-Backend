package com.edirnegezgini.placeservice.entity;

import com.edirnegezgini.commonservice.entity.BasePlace;
import com.edirnegezgini.commonservice.entity.Enum.PlaceCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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

    private byte[] image;

    private PlaceCategory category;
}
