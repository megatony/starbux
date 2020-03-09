package com.akgul.starbux.api.controller.response;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class StarbuxConflictApiResponse extends StarbuxApiResponse {
    public StarbuxConflictApiResponse(String message) {this.setMessage("BUSINESS ERROR : " + message);}
}
