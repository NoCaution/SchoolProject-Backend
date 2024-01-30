package com.edirnegezgini.commonservice.client;

import com.edirnegezgini.commonservice.entity.APIResponse;
import com.edirnegezgini.commonservice.entity.Response;

public interface IRestClient {
    <T> Response<T> send(ClientEntity clientEntity, Class<T> targetClass);

    APIResponse send(ClientEntity clientEntity);

}
