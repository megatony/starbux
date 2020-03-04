package com.akgul.starbux.service;

import com.akgul.starbux.controller.OrderController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderController orderController;
}
