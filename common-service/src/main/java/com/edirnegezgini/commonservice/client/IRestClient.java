package com.edirnegezgini.commonservice.client;

import com.edirnegezgini.commonservice.entity.APIResponse;
import com.edirnegezgini.commonservice.entity.Response;

public interface IRestClient {
    <T> Response<T> get(ClientEntity clientEntity, Class<T> targetClass);

    APIResponse get(ClientEntity clientEntity);

    <T> Response<T> post(ClientEntity clientEntity, Class<T> targetClass);

    APIResponse post(ClientEntity clientEntity);

    <T> Response<T> put(ClientEntity clientEntity, Class<T> targetClass);

    APIResponse put(ClientEntity clientEntity);

    <T> Response<T> delete(ClientEntity clientEntity, Class<T> targetClass);

    APIResponse delete(ClientEntity clientEntity);
}
