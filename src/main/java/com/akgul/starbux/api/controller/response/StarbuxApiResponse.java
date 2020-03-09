package com.akgul.starbux.api.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StarbuxApiResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
}
