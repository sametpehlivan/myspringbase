package com.pehlivan.security.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseData<T> extends Response {
    @Builder(builderMethodName = "responseDataBuilder")
    public ResponseData(String message, T data) {
        super(message);
        this.data = data;
    }

    private T data;
}
