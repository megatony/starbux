package com.akgul.starbux.api.controller.response;

import com.akgul.starbux.entity.Cart;
import com.akgul.starbux.entity.CartItem;
import com.akgul.starbux.enums.DiscountType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CartApiResponse extends StarbuxApiResponse {
    @JsonIgnore
    private Cart cart;

    private Long cartId;
    private BigDecimal totalAmount;
    private BigDecimal amount;
    private BigDecimal discountAmount;
    private DiscountType discountType;
    private List<CartItemApiResponse> cartItemResponses;

    public CartApiResponse(Cart cart) {
        if (ObjectUtils.isEmpty(cart)) {
            return;
        }

        this.cart = cart;
        this.cartId = cart.getId();
        this.totalAmount = cart.getTotalAmount();
        this.amount = cart.getAmount();
        this.discountAmount = cart.getDiscountAmount();

        if (!ObjectUtils.isEmpty(cart.getDiscount())) {
            this.discountType = cart.getDiscount().getDiscountType();
        }

        List<CartItemApiResponse> cartItemResponses = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            CartItemApiResponse cartItemResponse = new CartItemApiResponse(cartItem);
            cartItemResponses.add(cartItemResponse);
        }

        this.cartItemResponses = cartItemResponses;

    }



}
