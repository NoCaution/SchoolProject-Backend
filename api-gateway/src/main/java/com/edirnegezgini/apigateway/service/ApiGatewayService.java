package com.edirnegezgini.apigateway.service;

import com.edirnegezgini.commonservice.client.ClientEntity;
import com.edirnegezgini.commonservice.client.RestClient;
import com.edirnegezgini.commonservice.entity.Response;
import com.edirnegezgini.commonservice.entity.dto.UserDetailsDto;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ApiGatewayService {
    @Autowired
    public RestClient restClient;

    @Value("${application.service.user-service}")
    public String userServiceUrl;


    public Response<User> getUser(String token) {
        String url = userServiceUrl + "getAuthenticatedUser";

        ClientEntity clientEntity = new ClientEntity().get(
                url,
                token
        );

        Response<UserDetailsDto> getUserResponse = restClient.send(clientEntity, UserDetailsDto.class);



        if (getUserResponse.getHttpStatus() != HttpStatus.OK) {
            return new Response<>(
                    getUserResponse.getHttpStatus()
            );
        }

        UserDetailsDto userDetailsDto = getUserResponse.getResult();

        String id = userDetailsDto.getId().toString();
        String password = userDetailsDto.getPassword();
        Set<SimpleGrantedAuthority> role = Set.of(new SimpleGrantedAuthority(userDetailsDto.getRole().toString()));

        User user = new User(
                id,
                password,
                role
        );

        return new Response<>(
                user,
                HttpStatus.OK
        );
    }
}
