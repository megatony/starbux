package com.akgul.starbux.controller;

import com.akgul.starbux.entity.Cart;
import com.akgul.starbux.entity.User;
import com.akgul.starbux.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CartController {

    @Autowired
    private CartRepository repository;

    public Cart getCartByUser(User user) {
        return repository.getByUserAndDeleted(user, false);
    }

    public Cart saveCart(Cart cart) { return repository.save(cart);}
}
