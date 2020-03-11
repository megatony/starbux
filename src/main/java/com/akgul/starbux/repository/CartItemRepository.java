package com.akgul.starbux.repository;

import com.akgul.starbux.entity.CartItem;
import com.akgul.starbux.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByIdAndDeleted(Long cartItemId, boolean deleted);
    List<CartItem> findByDrinkProductAndDeleted(Product product, boolean deleted);
    List<CartItem> findBySideProductsContainingAndDeleted(Product product, boolean deleted);
}
