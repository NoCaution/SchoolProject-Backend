package com.edirnegezgini.placeservice.controller;

import com.edirnegezgini.commonservice.entity.APIResponse;
import com.edirnegezgini.placeservice.entity.dto.CreatePlaceDto;
import com.edirnegezgini.placeservice.entity.dto.UpdatePlaceDto;
import com.edirnegezgini.placeservice.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class PlaceController {
    @Autowired
    private PlaceService placeService;


    @GetMapping("/getAll")
    public APIResponse getAll(){
        return placeService.getAll();
    }

    @GetMapping("/getPlace/{id}")
    public APIResponse getPlace(@PathVariable UUID id){
        return placeService.getPlace(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createPlace")
    public APIResponse createPlace(@RequestBody CreatePlaceDto createPlaceDto){
        return placeService.createPlace(createPlaceDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updatePlace")
    public APIResponse updatePlace(@RequestBody UpdatePlaceDto updatePlaceDto){
        return placeService.updatePlace(updatePlaceDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deletePlace/{id}")
    public APIResponse deletePlace(@PathVariable UUID id){
        return placeService.deletePlace(id);
    }
}
