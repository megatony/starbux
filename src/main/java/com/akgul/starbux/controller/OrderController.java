package com.akgul.starbux.controller;

import com.akgul.starbux.entity.Order;
import com.akgul.starbux.entity.User;
import com.akgul.starbux.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository repository;

    public Order getOrderById(Long orderId) {
        return repository.getByIdAndDeleted(orderId, false);
    }

    public Order getOrdersByUser(User user) {
        return repository.getByUserAndDeleted(user, false);
    }

    public List<Order> getOrders() {
        return repository.findAllByDeleted(false);
    }
}
