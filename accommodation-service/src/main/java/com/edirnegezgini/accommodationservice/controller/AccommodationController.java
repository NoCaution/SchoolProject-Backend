package com.edirnegezgini.accommodationservice.controller;

import com.edirnegezgini.accommodationservice.entity.dto.CreateAccommodationDto;
import com.edirnegezgini.accommodationservice.entity.dto.UpdateAccommodationDto;
import com.edirnegezgini.accommodationservice.service.AccommodationService;
import com.edirnegezgini.commonservice.entity.APIResponse;
import com.edirnegezgini.commonservice.entity.AccommodationCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AccommodationController {
    @Autowired
    private AccommodationService accommodationService;


    @GetMapping("/getAccommodation/{id}")
    public APIResponse getAccommodation(@PathVariable UUID id) {
        return accommodationService.getAccommodation(id);
    }

    @GetMapping("/getAll")
    public APIResponse getAll() {
        return accommodationService.getAll();
    }

    @GetMapping("/getAllByCategory")
    public APIResponse getAllByCategory(@RequestParam AccommodationCategory category) {
        return accommodationService.getAllByCategory(category);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createAccommodation")
    public APIResponse createAccommodation(@RequestBody CreateAccommodationDto createAccommodationDto) {
        return accommodationService.createAccommodation(createAccommodationDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateAccommodation")
    public APIResponse updateAccommodation(@RequestBody UpdateAccommodationDto updateAccommodationDto) {
        return accommodationService.updateAccommodation(updateAccommodationDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteAccommodation/{id}")
    public APIResponse deleteAccommodation(@PathVariable UUID id) {
        return accommodationService.deleteAccommodation(id);
    }
}
