package com.akgul.starbux.api.controller;

import com.akgul.starbux.api.controller.response.*;
import com.akgul.starbux.entity.Cart;
import com.akgul.starbux.entity.Discount;
import com.akgul.starbux.entity.Order;
import com.akgul.starbux.entity.User;
import com.akgul.starbux.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
@RestController
@RequestMapping("/order")
public class OrderApiController {
    private Logger LOGGER = LoggerFactory.getLogger(OrderApiController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private DiscountService discountService;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public @ResponseBody
    List<OrderApiResponse> getOrdersByUser(Authentication authentication) {
        User user = userService.getUserByUserName(authentication.getName());
        List<Order> orders = orderService.getOrdersByUser(user);
        List<OrderApiResponse> apiResponses = new ArrayList<>();

        for (Order order : orders) {
            apiResponses.add(new OrderApiResponse(order));
        }

        return apiResponses;
    }

    @RequestMapping(value = {"/{orderId}"}, method = RequestMethod.GET)
    public @ResponseBody
    StarbuxApiResponse getOrderById(Authentication authentication, @PathVariable(value = "orderId") Long orderId) {
        User user = userService.getUserByUserName(authentication.getName());
        Order order = orderService.getUserOrderById(user, orderId);

        if (ObjectUtils.isEmpty(order)) {
            return new StarbuxNotFoundApiResponse("Order id " + orderId + " not found for user " + authentication.getName());
        }

        return new OrderApiResponse(order);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody
    StarbuxApiResponse createOrder(Authentication authentication) {
        User user = userService.getUserByUserName(authentication.getName());
        Cart cart = cartService.getCartByUser(user);

        if (ObjectUtils.isEmpty(cart)) {
            return new StarbuxConflictApiResponse("You are not ready to complete order. Please add products to cart.");
        }

        cart = cartService.applyDiscountForCart(cart);

        Order order = orderService.createOrder(cart, user);

        if (ObjectUtils.isEmpty(order)) {
            LOGGER.error("Order cannot be created for user " + authentication.getName());
            return new StarbuxConflictApiResponse("Creating order process failed.");
        }

        if (!ObjectUtils.isEmpty(cart.getDiscount())) {
            Discount discount = cart.getDiscount();
            discount.setOrder(order);
            discountService.saveDiscount(discount);
        }

        LOGGER.debug("Cart will be deleted for user " + authentication.getName() + " after successful order creating.");
        cart.setDeleted(true);
        cartItemService.deleteCartItems(cart.getCartItems());
        cartService.deleteCart(cart);

        StarbuxApiResponse apiResponse = new OrderApiResponse(order);
        apiResponse.setMessage("Order is created.");
        return apiResponse;
    }



}
