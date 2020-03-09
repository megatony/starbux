package com.akgul.starbux.api.controller.request;

import com.akgul.starbux.entity.Product;
import com.akgul.starbux.enums.ProductType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductApiRequest extends StarbuxApiRequest {

    @JsonIgnore
    private Product product;

    private String productName = "";
    private BigDecimal productPrice;
    private ProductType productType;

    public Product createProduct(ProductApiRequest apiRequest) {
        this.product = new Product();
        this.product.setProductName(productName);
        this.product.setPrice(productPrice);
        this.product.setProductType(productType);
        return this.product;
    }

}
