package com.akgul.starbux.service;

import com.akgul.starbux.controller.CartItemController;
import com.akgul.starbux.entity.Cart;
import com.akgul.starbux.entity.CartItem;
import com.akgul.starbux.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {

    @Autowired
    private CartItemController cartItemController;

    public CartItem createCartItem(Cart cart, Product product, int purchaseCount) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setDrinkProduct(product);
        cartItem.setQuantity(purchaseCount);
        return cartItem;
    }

    public CartItem getCartItemById(Long cartItemId) {
        return cartItemController.getCartItemById(cartItemId);
    }

    public List<CartItem> deleteCartItems(List<CartItem> cartItems) {
        for (CartItem cartItem : cartItems) {
            cartItem.setDeleted(true);
        }
        return cartItemController.saveCartItems(cartItems);
    }

    public CartItem saveCartItem(CartItem cartItem) {
        return cartItemController.saveCartItem(cartItem);
    }
}
