package com.edirnegezgini.favoriteservice.controller;

import com.edirnegezgini.commonservice.entity.APIResponse;
import com.edirnegezgini.favoriteservice.entity.dto.CreateFavoriteDto;
import com.edirnegezgini.favoriteservice.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;


    @GetMapping("/getFavorite/{id}")
    public APIResponse getFavorite(@PathVariable UUID id){
        return favoriteService.getFavorite(id);
    }

    @GetMapping("/getAll")
    public APIResponse getAll(){
        return favoriteService.getAll();
    }

    @PostMapping("/createFavorite")
    public APIResponse createFavorite(@RequestBody CreateFavoriteDto createFavoriteDto){
        return favoriteService.createFavorite(createFavoriteDto);
    }

    @DeleteMapping("/deleteFavorite/{id}")
    public APIResponse deleteFavorite(@PathVariable UUID id){
        return favoriteService.deleteFavorite(id);
    }
}
