package com.edirnegezgini.commonservice.client;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.http.HttpEntity;

@Getter
@Setter
@NoArgsConstructor
public class ClientEntity {
    private HttpEntity body;

    private String url;

    private String token;

    @Setter(AccessLevel.NONE)
    private RequestType requestType;

    private ClientEntity(String url, String token) {
        this.url = url;
        this.token = token;
    }

    private ClientEntity(String url, String token, HttpEntity body) {
        this.url = url;
        this.token = token;
        this.body = body;
    }

    public ClientEntity get(String url, String token) {
        this.requestType = RequestType.GET;
        return new ClientEntity(
                url,
                token
        );
    }

    public ClientEntity put(String url, String token, HttpEntity body) {
        this.requestType = RequestType.PUT;
        return new ClientEntity(
                url,
                token,
                body
        );
    }

    public ClientEntity post(String url, String token, HttpEntity body) {
        this.requestType = RequestType.POST;
        return new ClientEntity(
                url,
                token,
                body
        );
    }

    public ClientEntity delete(String url, String token) {
        this.requestType = RequestType.DELETE;
        return new ClientEntity(
                url,
                token
        );
    }
}
