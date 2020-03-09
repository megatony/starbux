package com.akgul.starbux.repository;

import com.akgul.starbux.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByIdAndDeleted(Long cartItemId, boolean deleted);
}
