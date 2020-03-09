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

    public List<Order> getOrdersByUser(User user) {
        return repository.findAllByUserEqualsAndDeleted(user, false);
    }

    public Order getUserOrderById(User user, Long orderId) {
        return repository.getByUserEqualsAndIdAndDeleted(user, orderId, false);
    }

    public Order saveOrder(Order order) {
        return repository.save(order);
    }


}
