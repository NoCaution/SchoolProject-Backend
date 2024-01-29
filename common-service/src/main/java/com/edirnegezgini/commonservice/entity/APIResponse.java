package com.edirnegezgini.commonservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class APIResponse {
    private HttpStatus httpStatus;

    private String message;

    private Object result;

    public APIResponse(HttpStatus httpStatus, String message){
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
