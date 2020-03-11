package com.akgul.starbux.service;

import com.akgul.starbux.controller.CartController;
import com.akgul.starbux.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartController cartController;

    @Autowired
    private DiscountService discountService;

    public Cart createCart(User user, Product product, int purchaseCount) {
        Cart cart = new Cart();
        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setQuantity(purchaseCount);
        cartItem.setDrinkProduct(product);
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);
        cart.setUser(user);
        return saveCart(cart);
    }

    public Cart getCartByUser(User user) {
        return cartController.getCartByUser(user);
    }

    public CartItem getCartItemOfDrinkProductFromCart(Cart cart, Product product) {
        CartItem result = null;

        for (CartItem cartItem : cart.getCartItems()) {
            if (cartItem.getDrinkProduct().equals(product)) {
                result = cartItem;
                if (cartItem.getSideProducts().size() == 0) {
                    return result;
                }
            }
        }
        return result;
    }

    public Cart applyDiscountForCart(Cart cart) {
        Discount discount = discountService.selectDiscountForCart(cart);

        if (ObjectUtils.isEmpty(discount)) {
            return cart;
        }

        cart.setDiscount(discount);
        return cart;
    }

    public Cart saveCart(Cart cart) {
        cart.prepareValues();
        cart = applyDiscountForCart(cart);
        return cartController.saveCart(cart);
    }

    public Cart deleteCart(Cart cart) {
        cart.setDeleted(true);
        return cartController.saveCart(cart);
    }
}
