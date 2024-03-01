package com.edirnegezgini.restaurantservice.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateRestaurantDto {
    private String title;

    private byte[] image;

    private String info;

    private String location;
}
