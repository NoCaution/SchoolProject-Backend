package com.edirnegezgini.restaurantservice.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRestaurantDto {
    private UUID id;

    private String title;

    private byte[] image;

    private String info;

    private String location;

}
