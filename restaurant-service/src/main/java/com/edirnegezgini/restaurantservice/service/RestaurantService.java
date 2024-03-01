package com.edirnegezgini.restaurantservice.service;

import com.edirnegezgini.commonservice.entity.APIResponse;
import com.edirnegezgini.commonservice.util.CustomModelMapper;
import com.edirnegezgini.restaurantservice.entity.Restaurant;
import com.edirnegezgini.restaurantservice.entity.dto.CreateRestaurantDto;
import com.edirnegezgini.restaurantservice.entity.dto.RestaurantDto;
import com.edirnegezgini.restaurantservice.entity.dto.UpdateRestaurantDto;
import com.edirnegezgini.restaurantservice.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CustomModelMapper customModelMapper;

    public APIResponse getRestaurant(UUID id) {
        Restaurant restaurant = findById(id);

        if (restaurant == null) {
            return new APIResponse(
                    HttpStatus.NOT_FOUND,
                    "no restaurant found"
            );
        }

        RestaurantDto restaurantDto = customModelMapper.map(restaurant, RestaurantDto.class);

        return new APIResponse(
                HttpStatus.OK,
                "success",
                restaurantDto
        );
    }

    public APIResponse getRestaurantByTitle(String title) {
        Restaurant restaurant = restaurantRepository.findByTitle(title);

        if (restaurant == null) {
            return new APIResponse(
                    HttpStatus.NOT_FOUND,
                    "no restaurant found"
            );
        }

        RestaurantDto restaurantDto = customModelMapper.map(restaurant, RestaurantDto.class);

        return new APIResponse(
                HttpStatus.OK,
                "success",
                restaurantDto
        );
    }

    public APIResponse getAll() {
        List<Restaurant> restaurantList = restaurantRepository.findAll();

        if (restaurantList.isEmpty()) {
            return new APIResponse(
                    HttpStatus.OK,
                    "success",
                    restaurantList
            );
        }

        List<RestaurantDto> restaurantDtoList = customModelMapper.convertList(restaurantList, RestaurantDto.class);

        return new APIResponse(
                HttpStatus.OK,
                "success",
                restaurantDtoList
        );
    }

    public APIResponse deleteRestaurant(UUID id) {
        Restaurant restaurant = findById(id);

        if (restaurant == null) {
            return new APIResponse(
                    HttpStatus.NOT_FOUND,
                    "no restaurant found"
            );
        }

        restaurantRepository.deleteById(id);

        return new APIResponse(
                HttpStatus.NO_CONTENT,
                "success"
        );
    }


    public APIResponse createRestaurant(CreateRestaurantDto createRestaurantDto) {
        Restaurant checkRestaurant = restaurantRepository.findByTitle(createRestaurantDto.getTitle());

        if (checkRestaurant != null) {
            return new APIResponse(
                    HttpStatus.BAD_REQUEST,
                    "this restaurant already exists"
            );
        }

        Restaurant restaurant = customModelMapper.map(createRestaurantDto, Restaurant.class);

        restaurantRepository.save(restaurant);

        return new APIResponse(
                HttpStatus.CREATED,
                "success"
        );
    }

    public APIResponse updateRestaurant(UpdateRestaurantDto updateRestaurantDto) {
        if (updateRestaurantDto.getId() == null) {
            return new APIResponse(
                    HttpStatus.BAD_REQUEST,
                    "restaurant id can not be null"
            );
        }

        Restaurant restaurant = findById(updateRestaurantDto.getId());

        if (restaurant == null) {
            return new APIResponse(
                    HttpStatus.NOT_FOUND,
                    "no restaurant found"
            );
        }

        Restaurant updatedRestaurant = updateFields(updateRestaurantDto, restaurant);

        restaurantRepository.save(updatedRestaurant);

        return new APIResponse(
                HttpStatus.OK,
                "success"
        );
    }

    private Restaurant findById(UUID id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    private Restaurant updateFields(UpdateRestaurantDto updateRestaurantDto, Restaurant restaurant) {
        restaurant.setInfo(updateRestaurantDto.getInfo());
        restaurant.setLocation(updateRestaurantDto.getLocation());
        restaurant.setTitle(updateRestaurantDto.getTitle());
        //restaurant.setImage(updateRestaurantDto.getImage());
        return restaurant;
    }


}
