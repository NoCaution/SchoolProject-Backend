package com.edirnegezgini.accommodationservice.service;

import com.edirnegezgini.accommodationservice.entity.Accommodation;
import com.edirnegezgini.accommodationservice.entity.dto.AccommodationDto;
import com.edirnegezgini.accommodationservice.entity.dto.CreateAccommodationDto;
import com.edirnegezgini.accommodationservice.entity.dto.UpdateAccommodationDto;
import com.edirnegezgini.accommodationservice.repository.AccommodationRepository;
import com.edirnegezgini.commonservice.entity.APIResponse;
import com.edirnegezgini.commonservice.entity.AccommodationCategory;
import com.edirnegezgini.commonservice.util.CustomModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccommodationService {
    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private CustomModelMapper customModelMapper;


    public APIResponse createAccommodation(CreateAccommodationDto createAccommodationDto) {
        Accommodation checkAccommodation = accommodationRepository.findByTitle(createAccommodationDto.getTitle());

        if (checkAccommodation != null) {
            return new APIResponse(
                    HttpStatus.BAD_REQUEST,
                    "this accommodation already exists"
            );
        }

        Accommodation accommodation = customModelMapper.map(createAccommodationDto, Accommodation.class);

        accommodationRepository.save(accommodation);

        return new APIResponse(
                HttpStatus.CREATED,
                "success"
        );
    }

    public APIResponse getAccommodation(UUID id) {
        Accommodation accommodation = findById(id);

        if (accommodation == null) {
            return new APIResponse(
                    HttpStatus.NOT_FOUND,
                    "no accommodation found"
            );
        }

        AccommodationDto accommodationDto = customModelMapper.map(accommodation, AccommodationDto.class);

        return new APIResponse(
                HttpStatus.OK,
                "success",
                accommodationDto
        );
    }

    public APIResponse getAll() {
        List<Accommodation> accommodationList = accommodationRepository.findAll();

        if (accommodationList.isEmpty()) {
            return new APIResponse(
                    HttpStatus.OK,
                    "success",
                    accommodationList
            );
        }

        List<AccommodationDto> accommodationDtoList = customModelMapper.convertList(accommodationList, AccommodationDto.class);

        return new APIResponse(
                HttpStatus.OK,
                "success",
                accommodationDtoList
        );
    }

    public APIResponse getAllByCategory(AccommodationCategory category) {
        List<Accommodation> accommodationList = accommodationRepository.findAllByCategory(category);

        if (accommodationList.isEmpty()) {
            return new APIResponse(
                    HttpStatus.OK,
                    "success",
                    accommodationList
            );
        }

        List<AccommodationDto> accommodationDtoList = customModelMapper.convertList(accommodationList, AccommodationDto.class);

        return new APIResponse(
                HttpStatus.OK,
                "success",
                accommodationDtoList
        );
    }

    public APIResponse updateAccommodation(UpdateAccommodationDto updateAccommodationDto) {
        Accommodation accommodation = findById(updateAccommodationDto.getId());

        if (accommodation == null) {
            return new APIResponse(
                    HttpStatus.NOT_FOUND,
                    "no accommodation found"
            );
        }

        Accommodation updatedAccommodation = updateFields(accommodation, updateAccommodationDto);

        accommodationRepository.save(updatedAccommodation);

        return new APIResponse(
                HttpStatus.OK,
                "success"
        );
    }

    public APIResponse deleteAccommodation(UUID id) {
        Accommodation accommodation = findById(id);

        if (accommodation == null) {
            return new APIResponse(
                    HttpStatus.NOT_FOUND,
                    "no accommodation found"
            );
        }

        accommodationRepository.deleteById(id);

        return new APIResponse(
                HttpStatus.NO_CONTENT,
                "success"
        );
    }

    private Accommodation findById(UUID id) {
        return accommodationRepository.findById(id).orElse(null);
    }

    private Accommodation updateFields(Accommodation accommodation, UpdateAccommodationDto updateAccommodationDto) {
        accommodation.setCategory(updateAccommodationDto.getCategory());
        accommodation.setInfo(updateAccommodationDto.getInfo());
        accommodation.setTitle(updateAccommodationDto.getTitle());
        accommodation.setLocation(updateAccommodationDto.getLocation());
        //accommodation.setImage(updateAccommodationDto.getImage());
        return accommodation;
    }
}
