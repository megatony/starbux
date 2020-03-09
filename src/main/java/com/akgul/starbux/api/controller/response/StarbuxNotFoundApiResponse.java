package com.akgul.starbux.api.controller.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class StarbuxNotFoundApiResponse extends StarbuxApiResponse {

    public StarbuxNotFoundApiResponse(String message) {
        this.setMessage("ERROR : " + message);
    }
}
