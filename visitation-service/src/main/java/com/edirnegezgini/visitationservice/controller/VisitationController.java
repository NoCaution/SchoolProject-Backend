package com.edirnegezgini.visitationservice.controller;

import com.edirnegezgini.commonservice.entity.APIResponse;
import com.edirnegezgini.commonservice.entity.BasePlaceCategory;
import com.edirnegezgini.visitationservice.entity.dto.CreateVisitationDto;
import com.edirnegezgini.visitationservice.service.VisitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class VisitationController {
    @Autowired
    private VisitationService visitationService;


    @GetMapping("/getVisitation/{id}")
    public APIResponse getVisitation(@PathVariable UUID id) {
        return visitationService.getVisitation(id);
    }

    @GetMapping("/getAllByCategory")
    public APIResponse getAllByCategory(@RequestParam BasePlaceCategory category) {
        return visitationService.getAllByCategory(category);
    }

    @PostMapping("/createVisitation")
    public APIResponse createVisitation(@RequestBody CreateVisitationDto createVisitationDto) {
        return visitationService.createVisitation(createVisitationDto);
    }

    @DeleteMapping("/deleteVisitation/{id}")
    public APIResponse deleteVisitation(@PathVariable UUID id) {
        return visitationService.deleteVisitation(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAll")
    public APIResponse getAll() {
        return visitationService.getAll();
    }
}
