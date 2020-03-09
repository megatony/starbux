package com.akgul.starbux.service;

import com.akgul.starbux.controller.CartItemController;
import com.akgul.starbux.entity.*;
import com.akgul.starbux.enums.DiscountType;
import com.akgul.starbux.enums.ProductType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartItemServiceTest {

    @InjectMocks
    private CartItemService cartItemService;

    @Mock
    private CartItemController controller;

    @Test
    public void shouldCreateCartItem() {
        User user = new User();
        user.setUserName("test");

        Product product = new Product();
        product.setProductName("Test product");
        product.setProductType(ProductType.DRINK);
        product.setPrice(BigDecimal.TEN);

        Cart cart = new Cart();
        cart.setUser(user);

        CartItem cartItem = cartItemService.createCartItem(cart, product, 1);

        Assert.assertEquals(cartItem.getCart(), cart);
        Assert.assertEquals(cartItem.getDrinkProduct(), product);
    }

    @Test
    public void shouldVerifyDeleteProcessHandlingWithSoftDelete() {
        Cart cart = new Cart();
        cart.setTotalAmount(BigDecimal.valueOf(12));

        Discount discount = new Discount();
        discount.setDiscountType(DiscountType.PercentageBased);
        discount.setDiscountAmount(BigDecimal.valueOf(3));

        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setQuantity(1);
        cartItem.setCart(cart);
        cartItem.setPrice(BigDecimal.TEN);
        cartItems.add(cartItem);
        cartItems.add(cartItem);
        cartItemService.deleteCartItems(cartItems);
        verify(controller, times(1)).saveCartItems(cartItems);
    }
}
