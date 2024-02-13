package com.edirnegezgini.commonservice.client;

import com.edirnegezgini.commonservice.entity.APIResponse;
import com.edirnegezgini.commonservice.entity.Response;
import com.edirnegezgini.commonservice.util.CustomModelMapper;
import com.google.gson.Gson;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RestClient implements IRestClient {
    @Autowired
    private Gson gson;

    @Autowired
    private CustomModelMapper customModelMapper;


    @Override
    public <T> Response<T> send(ClientEntity clientEntity, Class<T> targetClass) {
        Request request = buildRequest(clientEntity);
        String token = clientEntity.getToken();

        try {

            APIResponse response = sendRequest(request, token);

            if (response.getHttpStatus() != HttpStatus.OK) {
                return new Response<>(
                        response.getHttpStatus()
                );
            }

            T result = customModelMapper.map(response.getResult(), targetClass);

            return new Response<>(
                    result,
                    HttpStatus.OK
            );

        } catch (IOException e) {

            return new Response<>(
                    HttpStatus.INTERNAL_SERVER_ERROR
            );

        }
    }

    @Override
    public APIResponse send(ClientEntity clientEntity) {
        Request request = buildRequest(clientEntity);
        String token = clientEntity.getToken();

        try {

            return sendRequest(request, token);

        } catch (IOException e) {

            return new APIResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "error while sending request"
            );
        }
    }

    private APIResponse sendRequest(Request request, String token) throws IOException {
        String headerValue = "Bearer " + token;

        String json = request.setHeader("Authorization", headerValue).execute().returnContent().asString();
        return gson.fromJson(json, APIResponse.class);

    }

    private Request buildRequest(ClientEntity clientEntity) {
        Request request;
        RequestType requestType = clientEntity.getRequestType();
        String url = clientEntity.getUrl();

        switch (requestType) {
            case POST -> request = Request.Post(url).body(clientEntity.getBody());

            case PUT -> request = Request.Put(url).body(clientEntity.getBody());

            case DELETE -> request = Request.Delete(url);

            default -> request = Request.Get(url);
        }

        return request;
    }
}
