package com.edirnegezgini.commonservice.client;


import lombok.*;
import org.apache.http.HttpEntity;

@Getter
@NoArgsConstructor
public class ClientEntity {
    private HttpEntity body;

    private String url;

    private String token;

    @Setter(AccessLevel.NONE)
    private RequestType requestType;

    private ClientEntity(String url, String token, RequestType requestType) {
        this.url = url;
        this.token = token;
        this.requestType = requestType;
    }

    private ClientEntity(String url, String token, HttpEntity body, RequestType requestType) {
        this.url = url;
        this.token = token;
        this.body = body;
        this.requestType = requestType;
    }

    public ClientEntity get(String url, String token) {
        return new ClientEntity(
                url,
                token,
                RequestType.GET
        );
    }

    public ClientEntity put(String url, String token, HttpEntity body) {
        return new ClientEntity(
                url,
                token,
                body,
                RequestType.PUT
        );
    }

    public ClientEntity post(String url, String token, HttpEntity body) {
        return new ClientEntity(
                url,
                token,
                body,
                RequestType.POST
        );
    }

    public ClientEntity delete(String url, String token) {
        return new ClientEntity(
                url,
                token,
                RequestType.DELETE
        );
    }
}
