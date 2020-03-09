package com.akgul.starbux.api.controller.response;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class StarbuxMethodNotAllowedApiResponse extends StarbuxApiResponse {

    public StarbuxMethodNotAllowedApiResponse(String message) {
        this.setMessage(message);
    }
}
