package com.akgul.starbux.api.controller.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class MostUsedToppingApiResponse extends StarbuxApiResponse {
    private String drinkProductName;
    private String sideProductName;
    private BigInteger usingCount;

    public MostUsedToppingApiResponse(Map.Entry<String, List<Object[]>> input) {
        this.drinkProductName = input.getKey();

        for (Object[] objects : input.getValue()) {
            this.sideProductName = (String) objects[0];
            this.usingCount = (BigInteger) objects[1];
        }
    }
}
