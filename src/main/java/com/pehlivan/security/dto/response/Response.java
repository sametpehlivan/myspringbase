package com.pehlivan.security.dto.response;

import lombok.*;


@Getter
@Setter
public class Response {
    @Builder(builderMethodName = "responseBuilder")
    public Response(String message){
        this.message = message;
    }
    protected String message;
}
