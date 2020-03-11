package com.akgul.starbux.repository;

import com.akgul.starbux.entity.Cart;
import com.akgul.starbux.entity.CartItem;
import com.akgul.starbux.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart getByUserAndDeleted(User user, boolean deleted);
}
