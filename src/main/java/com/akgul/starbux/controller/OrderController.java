package com.akgul.starbux.controller;

import com.akgul.starbux.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository repository;
}
