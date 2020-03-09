package com.akgul.starbux.controller;

import com.akgul.starbux.entity.Order;
import com.akgul.starbux.entity.User;
import com.akgul.starbux.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderRepository orderRepository;

    @Test
    public void shouldGetOrdersByUser() {
        User user = new User();
        Order order = new Order();
        order.setUser(user);

        List<Order> orders = new ArrayList<>();
        orders.add(order);
        when(orderRepository.findAllByUserEqualsAndDeleted(user, false)).thenReturn(orders);

        Assert.assertEquals(orderController.getOrdersByUser(user), orders);
    }
}
