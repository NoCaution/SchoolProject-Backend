package com.edirnegezgini.favoriteservice.service;

import com.edirnegezgini.commonservice.client.ClientEntity;
import com.edirnegezgini.commonservice.client.RestClient;
import com.edirnegezgini.commonservice.entity.APIResponse;
import com.edirnegezgini.commonservice.entity.BasePlaceCategory;
import com.edirnegezgini.commonservice.service.CommonService;
import com.edirnegezgini.commonservice.util.CustomModelMapper;
import com.edirnegezgini.commonservice.util.JwtToken;
import com.edirnegezgini.favoriteservice.entity.Favorite;
import com.edirnegezgini.favoriteservice.entity.dto.CreateFavoriteDto;
import com.edirnegezgini.favoriteservice.entity.dto.FavoriteDto;
import com.edirnegezgini.favoriteservice.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FavoriteService {
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
    private FavoriteRepository favoriteRepository;

    @Autowired
    private CustomModelMapper customModelMapper;

    @Autowired
    private RestClient restClient;

    @Autowired
    private CommonService commonService;

    public APIResponse getFavorite(UUID id) {
        User authenticatedUser = commonService.getLoggedInUser();
        UUID authenticatedUserId = UUID.fromString(authenticatedUser.getUsername());
        boolean isNotAdminRequest = !commonService.isAdminRequest(authenticatedUser);

        Favorite favorite = findById(id);

        if (isNotAdminRequest) {
            boolean isIllegalRequest = favorite.getUserId() != authenticatedUserId;
            if (isIllegalRequest) {
                return new APIResponse(
                        HttpStatus.FORBIDDEN,
                        "need permission!"
                );
            }
        }

        if (favorite == null) {
            return new APIResponse(
                    HttpStatus.NOT_FOUND,
                    "no favorite found"
            );
        }

        FavoriteDto favoriteDto = customModelMapper.map(favorite, FavoriteDto.class);

        return new APIResponse(
                HttpStatus.OK,
                "success",
                favoriteDto
        );
    }


    public APIResponse getAll() {
        List<Favorite> favoriteList;
        User authenticatedUser = commonService.getLoggedInUser();
        UUID authenticatedUserId = UUID.fromString(authenticatedUser.getUsername());

        boolean isAdminRequest = commonService.isAdminRequest(authenticatedUser);

        if (isAdminRequest) {
            favoriteList = favoriteRepository.findAll();
        } else {
            favoriteList = favoriteRepository.findAllByUserId(authenticatedUserId);
        }

        if (favoriteList.isEmpty()) {
            return new APIResponse(
                    HttpStatus.OK,
                    "success",
                    favoriteList
            );
        }

        List<FavoriteDto> favoriteDtoList = customModelMapper.convertList(favoriteList, FavoriteDto.class);

        return new APIResponse(
                HttpStatus.OK,
                "success",
                favoriteDtoList
        );
    }


    public APIResponse createFavorite(CreateFavoriteDto createFavoriteDto) {
        User authenticatedUser = commonService.getLoggedInUser();
        UUID authenticatedUserId = UUID.fromString(authenticatedUser.getUsername());
        boolean isNotAdminRequest = !commonService.isAdminRequest(authenticatedUser);
        if (createFavoriteDto.getFavoritePlaceId() == null) {
            return new APIResponse(
                    HttpStatus.BAD_REQUEST,
                    "favorite place id can not be null"
            );
        }

        if (isNotAdminRequest) {
            boolean isIllegalRequest = authenticatedUserId != createFavoriteDto.getUserId();
            if (isIllegalRequest) {
                return new APIResponse(
                        HttpStatus.FORBIDDEN,
                        "need permission!"
                );
            }
        }

        //build url
        String url = buildUrl(createFavoriteDto.getCategory(), createFavoriteDto.getFavoritePlaceId());

        //send request to the service in question
        String token = jwtToken.getToken(secretKey);

        if (token == null) {
            return new APIResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "failed while retrieving token"
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

        //check if there is such place in database
        if (response.getHttpStatus() == HttpStatus.NOT_FOUND) {
            return response;
        }

        //save favorite
        Favorite favorite = customModelMapper.map(createFavoriteDto, Favorite.class);

        favoriteRepository.save(favorite);

        return new APIResponse(
                HttpStatus.CREATED,
                "success"
        );
    }

    public APIResponse deleteFavorite(UUID id) {
        User authenticatedUser = commonService.getLoggedInUser();
        UUID authenticatedUserId = UUID.fromString(authenticatedUser.getUsername());
        boolean isNotAdminRequest = !commonService.isAdminRequest(authenticatedUser);
        Favorite favorite = findById(id);

        if (favorite == null) {
            return new APIResponse(
                    HttpStatus.NOT_FOUND,
                    "no favorite found"
            );
        }

        if (isNotAdminRequest) {
            boolean isIllegalRequest = favorite.getUserId() != authenticatedUserId;
            if (isIllegalRequest) {
                return new APIResponse(
                        HttpStatus.FORBIDDEN,
                        "need permission!"
                );
            }
        }

        favoriteRepository.deleteById(id);

        return new APIResponse(
                HttpStatus.NO_CONTENT,
                "success"
        );
    }

    private String buildUrl(BasePlaceCategory category, UUID uuid) {
        String url;
        StringBuilder stringBuilder = new StringBuilder();
        String id = uuid.toString();

        switch (category) {
            case restaurant -> stringBuilder.append(restaurantServiceUrl).append("getRestaurant/").append(id);

            case accommodation -> stringBuilder.append(accommodationServiceUrl).append("getAccommodation/").append(id);

            default -> stringBuilder.append(placeServiceUrl).append("getPlace/").append(id);
        }

        url = stringBuilder.toString();
        return url;
    }

    private Favorite findById(UUID id) {
        return favoriteRepository.findById(id).orElse(null);
    }
}
