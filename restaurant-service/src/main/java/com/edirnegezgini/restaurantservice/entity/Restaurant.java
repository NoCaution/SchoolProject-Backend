package com.edirnegezgini.restaurantservice.entity;

import com.edirnegezgini.commonservice.entity.BasePlace;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "restaurants")
public class Restaurant extends BasePlace {
    private String title;

    private String image;

    @Column(length = 2048)
    private String info;

    private String location;
}
