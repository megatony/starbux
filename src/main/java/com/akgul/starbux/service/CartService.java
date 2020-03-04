package com.akgul.starbux.service;

import com.akgul.starbux.controller.CartController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartController cartController;
}
