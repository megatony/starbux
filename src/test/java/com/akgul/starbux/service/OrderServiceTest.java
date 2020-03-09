package com.akgul.starbux.service;

import com.akgul.starbux.controller.OrderController;
import com.akgul.starbux.entity.Cart;
import com.akgul.starbux.entity.Order;
import com.akgul.starbux.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderController orderController;

    @Test
    public void shouldNotCreateOrderWhenCartIsNotAvailable() {
        Assert.assertNull(orderService.createOrder(null, new User()));
    }

    @Test
    public void shouldNotCreateOrderWhenUserIsNotAvailable() {
        Assert.assertNull(orderService.createOrder(new Cart(), null));
    }

    @Test
    public void shouldNotCreateOrderWhenCartAmountIsMinus() {
        Cart cart = new Cart();
        cart.setAmount(BigDecimal.valueOf(-1));

        Assert.assertNull(orderService.createOrder(cart, new User()));
    }

    @Test
    public void shouldCreateOrderIfApplicable() {
        User user = new User();
        Cart cart = new Cart();
        cart.setTotalAmount(BigDecimal.TEN);
        cart.setAmount(BigDecimal.TEN);
        cart.setDiscountAmount(BigDecimal.ZERO);
        cart.setUser(user);

        Order expectedOrder = new Order();
        expectedOrder.setUser(user);
        expectedOrder.setCart(cart);
        expectedOrder.setTotalAmount(BigDecimal.TEN);
        expectedOrder.setAmount(BigDecimal.TEN);
        expectedOrder.setDiscountAmount(BigDecimal.ZERO);
        when(orderController.saveOrder(any())).thenReturn(expectedOrder);

        Order order = orderService.createOrder(cart, user);

        Assert.assertEquals(order.getCart(), expectedOrder.getCart());
    }
}
