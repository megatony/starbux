package com.akgul.starbux.api.controller.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class MostUsedToppingApiResponse extends StarbuxApiResponse {
    private BigInteger sideProductId;
    private BigInteger usingCount;

    public MostUsedToppingApiResponse(Object[] input) {
        this.sideProductId = (BigInteger) input[0];
        this.usingCount = (BigInteger) input[1];
    }
}
