package com.akgul.starbux.service;

import com.akgul.starbux.controller.CartItemController;
import com.akgul.starbux.entity.Cart;
import com.akgul.starbux.entity.CartItem;
import com.akgul.starbux.entity.Product;
import com.akgul.starbux.enums.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<CartItem> getCartItemsByDrinkProduct(Product product) {
        return cartItemController.getDrinkProductCartItemsByProduct(product);
    }

    public List<CartItem> getCartItemsBySideProduct(Product product) {
        return cartItemController.getSideProductCartItemsByProduct(product);
    }

    public List<CartItem> getCartItemsByProduct(Product product) {
        if (product.getProductType().equals(ProductType.DRINK)) {
            return getCartItemsByDrinkProduct(product);
        } else if (product.getProductType().equals(ProductType.SIDE)) {
            return getCartItemsBySideProduct(product);
        } else {
            return new ArrayList<>();
        }
    }
}
