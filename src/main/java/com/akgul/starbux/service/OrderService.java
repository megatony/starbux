package com.akgul.starbux.service;

import com.akgul.starbux.controller.OrderController;
import com.akgul.starbux.entity.Order;
import com.akgul.starbux.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderController orderController;

    @Autowired
    private UserService userService;

    public Order getOrderById(Long orderId) {
        return orderController.getOrderById(orderId);
    }

    public Order getOrderByUser(User user) {
        return orderController.getOrdersByUser(user);
    }

    public Order getOrderByUserId(Long userId) {
        User user = userService.getUserById(userId);
        return orderController.getOrdersByUser(user);
    }

    public List<Order> getAllOrders() {
        return orderController.getOrders();
    }
}
