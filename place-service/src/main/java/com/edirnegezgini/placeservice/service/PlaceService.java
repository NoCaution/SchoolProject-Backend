package com.edirnegezgini.placeservice.service;

import com.edirnegezgini.commonservice.entity.APIResponse;
import com.edirnegezgini.commonservice.util.CustomModelMapper;
import com.edirnegezgini.placeservice.entity.dto.CreatePlaceDto;
import com.edirnegezgini.placeservice.entity.dto.PlaceDto;
import com.edirnegezgini.placeservice.entity.dto.UpdatePlaceDto;
import com.edirnegezgini.placeservice.entity.Place;
import com.edirnegezgini.placeservice.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlaceService {
    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private CustomModelMapper customModelMapper;

    public APIResponse getAll() {
        List<Place> placeList = placeRepository.findAll();

        if (placeList.isEmpty()) {
            return new APIResponse(
                    HttpStatus.OK,
                    "success",
                    placeList
            );
        }

        List<PlaceDto> placeDtoList = customModelMapper.convertList(placeList, PlaceDto.class);

        return new APIResponse(
                HttpStatus.OK,
                "success",
                placeDtoList
        );
    }

    public APIResponse getPlace(UUID id) {
        Place place = findById(id);

        if (place == null) {
            return new APIResponse(
                    HttpStatus.NOT_FOUND,
                    "no place found"
            );
        }

        PlaceDto placeDto = customModelMapper.map(place, PlaceDto.class);

        return new APIResponse(
                HttpStatus.OK,
                "success",
                placeDto
        );
    }

    public APIResponse createPlace(CreatePlaceDto createPlaceDto) {
        Place checkPlace = placeRepository.findByTitle(createPlaceDto.getTitle());

        if (checkPlace != null) {
            return new APIResponse(
                    HttpStatus.BAD_REQUEST,
                    "this place already exists"
            );
        }

        Place place = customModelMapper.map(createPlaceDto, Place.class);

        placeRepository.save(place);

        return new APIResponse(
                HttpStatus.CREATED,
                "success"
        );
    }

    public APIResponse updatePlace(UpdatePlaceDto updatePlaceDto) {
        if (updatePlaceDto.getId() == null) {
            return new APIResponse(
                    HttpStatus.BAD_REQUEST,
                    "place id can not be null"
            );
        }

        Place place = findById(updatePlaceDto.getId());

        if (place == null) {
            return new APIResponse(
                    HttpStatus.NOT_FOUND,
                    "no place found"
            );
        }

        Place updatedPlace = updatePlaceFields(updatePlaceDto, place);
        PlaceDto placeDto = customModelMapper.map(updatedPlace, PlaceDto.class);

        placeRepository.save(updatedPlace);

        return new APIResponse(
                HttpStatus.OK,
                "success",
                placeDto
        );
    }

    public APIResponse deletePlace(UUID id) {
        if (id == null) {
            return new APIResponse(
                    HttpStatus.BAD_REQUEST,
                    "place id can not be null"
            );
        }

        Place place = findById(id);

        if (place == null) {
            return new APIResponse(
                    HttpStatus.NOT_FOUND,
                    "no place found"
            );
        }

        placeRepository.deleteById(id);

        return new APIResponse(
                HttpStatus.OK,
                "success"
        );
    }

    private Place findById(UUID id) {
        return placeRepository.findById(id).orElse(null);
    }

    private Place updatePlaceFields(UpdatePlaceDto placeDto, Place place) {
        place.setTitle(placeDto.getTitle());
        //place.setImage(place.getImage());
        place.setLocation(place.getLocation());
        place.setInfo(placeDto.getInfo());
        place.setCategory(placeDto.getCategory());
        return place;
    }

}
