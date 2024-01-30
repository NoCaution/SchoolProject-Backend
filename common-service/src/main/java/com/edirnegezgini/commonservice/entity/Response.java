package com.edirnegezgini.commonservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    private T result;

    private HttpStatus httpStatus;

    public Response(HttpStatus httpStatus){
        this.httpStatus = httpStatus;
    }
}
