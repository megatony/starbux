package com.akgul.starbux.api.controller.response;

import com.akgul.starbux.entity.CartItem;
import com.akgul.starbux.entity.Product;
import com.akgul.starbux.enums.ProductType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CartItemApiResponse extends StarbuxApiResponse {
    @JsonIgnore
    private CartItem cartItem;

    private Long cartItemId;
    private BigDecimal cartItemPrice;
    private int quantity;
    private ProductApiResponse drinkProduct;
    private List<ProductApiResponse> sideProducts;

    public CartItemApiResponse(CartItem cartItem) {
        if (ObjectUtils.isEmpty(cartItem)) {
            return;
        }

        this.cartItem = cartItem;
        this.cartItemId = cartItem.getId();
        this.cartItemPrice = cartItem.getPrice();
        this.quantity = cartItem.getQuantity();
        this.drinkProduct = new ProductApiResponse(cartItem.getDrinkProduct());

        List<ProductApiResponse> productResponses = new ArrayList<>();
        for (Product product : cartItem.getProducts()) {
            if (ProductType.SIDE.equals(product.getProductType())) {
                ProductApiResponse productResponse = new ProductApiResponse(product);
                productResponses.add(productResponse);
            }
        }
        this.sideProducts = productResponses;
    }
}
