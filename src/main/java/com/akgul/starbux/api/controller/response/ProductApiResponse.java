package com.akgul.starbux.api.controller.response;

import com.akgul.starbux.entity.Product;
import com.akgul.starbux.enums.ProductType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.internal.util.StringHelper;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductApiResponse extends StarbuxApiResponse {
    @JsonIgnore
    private Product product;

    private Long productId;
    private String productName = "";
    private BigDecimal productPrice;
    private ProductType productType;

    public ProductApiResponse(Product product) {
        if (ObjectUtils.isEmpty(product)) {
            return;
        }

        this.product = product;
        this.productId = product.getId();

        if (!StringHelper.isEmpty(product.getProductName())) {
            this.productName = product.getProductName();
        }

        if (!ObjectUtils.isEmpty(product.getProductType())) {
            this.productType = product.getProductType();
        }

        this.productPrice = product.getPrice();
    }
}
