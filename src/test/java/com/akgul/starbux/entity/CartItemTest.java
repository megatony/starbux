package com.akgul.starbux.entity;

import com.akgul.starbux.enums.ProductType;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class CartItemTest {

    @Test
    public void shouldCalculatePrice() {
        Product product = new Product();
        product.setProductType(ProductType.DRINK);
        product.setPrice(BigDecimal.TEN);

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(3);
        cartItem.setDrinkProduct(product);
        cartItem.calculatePrice();
        Assert.assertEquals(cartItem.getPrice().toString(), "30");
    }
}
