package com.akgul.starbux.api.controller.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
public class TotalAmountOfOrdersApiResponse extends StarbuxApiResponse {
    private BigDecimal amount;
    private BigInteger userId;

    public TotalAmountOfOrdersApiResponse(Object[] rawResponse) {
        this.amount = (BigDecimal) rawResponse[0];
        this.userId = (BigInteger) rawResponse[1];
    }
}
