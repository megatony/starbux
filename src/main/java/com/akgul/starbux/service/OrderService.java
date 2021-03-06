package com.akgul.starbux.service;

import com.akgul.starbux.controller.OrderController;
import com.akgul.starbux.entity.Cart;
import com.akgul.starbux.entity.Order;
import com.akgul.starbux.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {
    private Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    private static final BigDecimal MINIMUM_ORDER_AMOUNT = BigDecimal.valueOf(0.01);

    @Autowired
    private OrderController orderController;

    public Order getUserOrderById(User user, Long orderId) {
        return orderController.getUserOrderById(user, orderId);
    }

    public List<Order> getOrdersByUser(User user) {
        return orderController.getOrdersByUser(user);
    }

    public Order createOrder(Cart cart, User user) {
        if (ObjectUtils.isEmpty(cart) || ObjectUtils.isEmpty(user)) {
            LOGGER.error("Cart or user is not available.");
            return null;
        }

        if (cart.getAmount().compareTo(MINIMUM_ORDER_AMOUNT) < 0) {
            LOGGER.error("Cart is empty. Order will not be created.");
            return null;
        }

        Order order = new Order();
        order.setCart(cart);
        order.setTotalAmount(cart.getTotalAmount());
        order.setAmount(cart.getAmount());
        order.setDiscountAmount(cart.getDiscountAmount());
        order.setDiscount(cart.getDiscount());
        order.setUser(user);
        orderController.saveOrder(order);
        return order;
    }
}
