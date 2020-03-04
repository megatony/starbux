package com.akgul.starbux.api.controller;

import com.akgul.starbux.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderApiController {

    @Autowired
    private OrderService orderService;
}
