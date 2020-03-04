package com.akgul.starbux.controller;

import com.akgul.starbux.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CartController {

    @Autowired
    private CartRepository repository;
}
