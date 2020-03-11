package com.akgul.starbux.api.controller.response;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class StarbuxConflictApiResponse extends StarbuxApiResponse {

    public StarbuxConflictApiResponse(String message) {
        LoggerFactory.getLogger(StarbuxConflictApiResponse.class).warn(message);
        this.setMessage("BUSINESS ERROR : " + message);}
}
