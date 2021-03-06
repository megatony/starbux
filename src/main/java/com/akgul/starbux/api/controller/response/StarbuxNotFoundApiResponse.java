package com.akgul.starbux.api.controller.response;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StarbuxNotFoundApiResponse extends StarbuxApiResponse {

    public StarbuxNotFoundApiResponse(String message) {
        LoggerFactory.getLogger(StarbuxNotFoundApiResponse.class).error(message);
        this.setMessage("ERROR : " + message);
    }
}
