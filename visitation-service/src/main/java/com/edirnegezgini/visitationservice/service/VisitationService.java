package com.edirnegezgini.visitationservice.service;

import com.edirnegezgini.commonservice.client.ClientEntity;
import com.edirnegezgini.commonservice.client.RestClient;
import com.edirnegezgini.commonservice.entity.APIResponse;
import com.edirnegezgini.commonservice.entity.BasePlaceCategory;
import com.edirnegezgini.commonservice.service.CommonService;
import com.edirnegezgini.commonservice.util.CustomModelMapper;
import com.edirnegezgini.commonservice.util.JwtToken;
import com.edirnegezgini.visitationservice.entity.Visitation;
import com.edirnegezgini.visitationservice.entity.dto.CreateVisitationDto;
import com.edirnegezgini.visitationservice.entity.dto.VisitationDto;
import com.edirnegezgini.visitationservice.repository.VisitationRepository;
import com.google.common.base.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VisitationService {
    @Value("${application.service.accommodation-service}")
    private String accommodationServiceUrl;

    @Value("${application.service.place-service}")
    private String placeServiceUrl;

    @Value("${application.service.restaurant-service}")
    private String restaurantServiceUrl;

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private RestClient restClient;

    @Autowired
    private VisitationRepository visitationRepository;

    @Autowired
    private CustomModelMapper customModelMapper;

    @Autowired
    private CommonService commonService;


    public APIResponse getVisitation(UUID id) {
        Visitation visitation = getById(id);

        if (visitation == null) {
            return new APIResponse(
                    HttpStatus.NOT_FOUND,
                    "no visitation found"
            );
        }

        VisitationDto visitationDto = customModelMapper.map(visitation, VisitationDto.class);

        return new APIResponse(
                HttpStatus.OK,
                "success",
                visitationDto
        );
    }

    public APIResponse getAuthenticatedUserVisitations() {
        UUID authenticatedUserId = UUID.fromString(commonService.getLoggedInUser().getUsername());
        List<Visitation> visitationList = visitationRepository.findAllByUserId(authenticatedUserId);

        if(visitationList.isEmpty()) {
            return new APIResponse(
                    HttpStatus.OK,
                    "success",
                    visitationList
            );
        }

        List<VisitationDto> visitationDtoList = customModelMapper.convertList(visitationList, VisitationDto.class);

        return new APIResponse(
                HttpStatus.OK,
                "success",
                visitationDtoList
        );
    }

    public APIResponse getAll() {
        List<Visitation> visitationList = visitationRepository.findAll();

        if (visitationList.isEmpty()) {
            return new APIResponse(
                    HttpStatus.OK,
                    "success",
                    visitationList
            );
        }

        List<VisitationDto> visitationDtoList = customModelMapper.convertList(visitationList, VisitationDto.class);

        return new APIResponse(
                HttpStatus.OK,
                "success",
                visitationDtoList
        );
    }

    public APIResponse getAllByCategory(BasePlaceCategory category) {
        User authenticatedUser = commonService.getLoggedInUser();
        UUID authenticatedUserId = UUID.fromString(authenticatedUser.getUsername());
        boolean isAdminRequest = commonService.isAdminRequest(authenticatedUser);

        List<Visitation> visitationList;

        if (isAdminRequest) {
            visitationList = visitationRepository.findAllByCategory(category);
        } else {
            visitationList = visitationRepository.findAllByCategoryAndUserId(category, authenticatedUserId);
        }

        if (visitationList.isEmpty()) {
            return new APIResponse(
                    HttpStatus.OK,
                    "success",
                    visitationList
            );
        }

        List<VisitationDto> visitationDtoList = customModelMapper.convertList(visitationList, VisitationDto.class);

        return new APIResponse(
                HttpStatus.OK,
                "success",
                visitationDtoList
        );
    }

    public APIResponse createVisitation(CreateVisitationDto createVisitationDto) {
        User authenticatedUser = commonService.getLoggedInUser();
        UUID authenticatedUserId = UUID.fromString(authenticatedUser.getUsername());

        boolean isIllegalRequest = !Objects.equal(authenticatedUserId, createVisitationDto.getUserId());
        if (isIllegalRequest) {
            return new APIResponse(
                    HttpStatus.FORBIDDEN,
                    "permission needed!"
            );
        }

        if (createVisitationDto.getVisitedPlaceId() == null) {
            return new APIResponse(
                    HttpStatus.BAD_REQUEST,
                    "visited place id can not be null"
            );
        }

        //build url and send request to service in question
        String token = jwtToken.getToken(secretKey);
        String url = buildUrl(createVisitationDto.getCategory(), createVisitationDto.getVisitedPlaceId());

        if (token == null) {
            return new APIResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "error while retrieving token"
            );
        }


        ClientEntity clientEntity = new ClientEntity().get(url, token);

        APIResponse response = restClient.send(clientEntity);

        if (response == null) {
            return new APIResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "error while retrieving response"
            );
        }

        if (response.getHttpStatus() == HttpStatus.NOT_FOUND) {
            return response;
        }

        //save visitation to database
        Visitation visitation = customModelMapper.map(createVisitationDto, Visitation.class);

        visitationRepository.save(visitation);

        return new APIResponse(
                HttpStatus.CREATED,
                "success"
        );
    }

    public APIResponse deleteVisitation(UUID id) {
        User authenticatedUser = commonService.getLoggedInUser();
        UUID authenticatedUserId = UUID.fromString(authenticatedUser.getUsername());
        Visitation visitation = getById(id);

        if (visitation == null) {
            return new APIResponse(
                    HttpStatus.NOT_FOUND,
                    "no visitation found"
            );
        }

        boolean isIllegalRequest = !Objects.equal(authenticatedUserId, visitation.getUserId());
        if (isIllegalRequest) {
            return new APIResponse(
                    HttpStatus.FORBIDDEN,
                    "permission needed!"
            );
        }

        visitationRepository.deleteById(id);

        return new APIResponse(
                HttpStatus.NO_CONTENT,
                "success"
        );
    }


    //builds url by deciding which service it will be based on BasePlaceCategory
    private String buildUrl(BasePlaceCategory category, UUID uuid) {
        StringBuilder stringBuilder = new StringBuilder();
        String id = uuid.toString();

        switch (category) {
            case restaurant -> stringBuilder.append(restaurantServiceUrl).append("getRestaurant/").append(id);

            case accommodation -> stringBuilder.append(accommodationServiceUrl).append("getAccommodation/").append(id);

            default -> stringBuilder.append(placeServiceUrl).append("getPlace/").append(id);
        }

        return stringBuilder.toString();
    }

    private Visitation getById(UUID id) {
        return visitationRepository.findById(id).orElse(null);
    }

}
