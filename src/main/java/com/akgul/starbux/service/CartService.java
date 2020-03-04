package com.akgul.starbux.service;

import com.akgul.starbux.controller.CartController;
import com.akgul.starbux.entity.Cart;
import com.akgul.starbux.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartController cartController;

    @Autowired
    private UserService userService;

    public Cart getCartById(Long cartId) {
        return cartController.getCartById(cartId);
    }

    public Cart getCartByUser(User user) {
        return cartController.getCartByUser(user);
    }

    public Cart getCartByUserId(Long userId) {
        User user = userService.getUserById(userId);
        return cartController.getCartByUser(user);
    }
}
