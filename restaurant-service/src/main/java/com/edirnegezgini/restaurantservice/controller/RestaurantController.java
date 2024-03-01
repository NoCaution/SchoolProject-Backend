package com.edirnegezgini.restaurantservice.controller;

import com.edirnegezgini.commonservice.entity.APIResponse;
import com.edirnegezgini.restaurantservice.entity.dto.CreateRestaurantDto;
import com.edirnegezgini.restaurantservice.entity.dto.UpdateRestaurantDto;
import com.edirnegezgini.restaurantservice.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/getRestaurant/{id}")
    public APIResponse getRestaurant(@PathVariable UUID id) {
        return restaurantService.getRestaurant(id);
    }

    @GetMapping("/getAll")
    public APIResponse getAll() {
        return restaurantService.getAll();
    }

    @GetMapping("/getRestaurantByTitle/{title}")
    public APIResponse getRestaurantByTitle(@PathVariable String title) {
        return restaurantService.getRestaurantByTitle(title);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createRestaurant")
    public APIResponse createRestaurant(@RequestBody CreateRestaurantDto createRestaurantDto) {
        return restaurantService.createRestaurant(createRestaurantDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateRestaurant")
    public APIResponse updateRestaurant(@RequestBody UpdateRestaurantDto updateRestaurantDto) {
        return restaurantService.updateRestaurant(updateRestaurantDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteRestaurant/{id}")
    public APIResponse deleteRestaurant(@PathVariable UUID id) {
        return restaurantService.deleteRestaurant(id);
    }
}
