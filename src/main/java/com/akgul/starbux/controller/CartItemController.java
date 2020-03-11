package com.akgul.starbux.controller;

import com.akgul.starbux.entity.CartItem;
import com.akgul.starbux.entity.Product;
import com.akgul.starbux.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CartItemController {

    @Autowired
    private CartItemRepository repository;

    public CartItem getCartItemById(Long cartItemId) {
        return repository.findByIdAndDeleted(cartItemId, false);
    }

    public List<CartItem> saveCartItems(List<CartItem> cartItems) {
        return repository.saveAll(cartItems);
    }

    public CartItem saveCartItem(CartItem cartItem) {
        return repository.save(cartItem);
    }

    public List<CartItem> getDrinkProductCartItemsByProduct(Product product) {
        return repository.findByDrinkProductAndDeleted(product, false);
    }

    public List<CartItem> getSideProductCartItemsByProduct(Product product) {
        return repository.findBySideProductsContainingAndDeleted(product, false);
    }
}
