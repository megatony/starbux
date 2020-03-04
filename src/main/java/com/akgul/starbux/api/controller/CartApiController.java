package com.akgul.starbux.api.controller;

import com.akgul.starbux.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartApiController {

    @Autowired
    private CartService cartService;
}
