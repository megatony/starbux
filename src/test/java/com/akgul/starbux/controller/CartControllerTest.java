package com.akgul.starbux.controller;

import com.akgul.starbux.entity.Cart;
import com.akgul.starbux.entity.User;
import com.akgul.starbux.repository.CartRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartControllerTest {

    @InjectMocks
    private CartController cartController;

    @Mock
    private CartRepository cartRepository;

    @Test
    public void shouldGetCartByUser() {
        User user = new User();
        Cart cart = new Cart();
        cart.setUser(user);
        when(cartRepository.getByUserAndDeleted(user, false)).thenReturn(cart);

        Assert.assertEquals(cartController.getCartByUser(user), cart);
    }

    @Test
    public void shouldNotGetDeletedCartByUser() {
        User user = new User();
        Cart cart = new Cart();
        cart.setDeleted(true);
        cart.setUser(user);
        when(cartRepository.getByUserAndDeleted(user, false)).thenReturn(null);

        Assert.assertEquals(cartController.getCartByUser(user), null);
    }
}
