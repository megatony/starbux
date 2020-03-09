package com.akgul.starbux.entity;

import com.akgul.starbux.enums.ProductType;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

public class CartItemTest {

    @Test
    public void shouldCalculatePrice() {
        Product product = new Product();
        product.setProductType(ProductType.DRINK);
        product.setPrice(BigDecimal.TEN);

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(3);
        cartItem.setProducts(new ArrayList<>());
        cartItem.setDrinkProduct(product);
        cartItem.calculatePrice();
        Assert.assertEquals(cartItem.getPrice().toString(), "30");
    }
}
