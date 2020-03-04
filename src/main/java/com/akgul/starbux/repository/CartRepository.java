package com.akgul.starbux.repository;

import com.akgul.starbux.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart getByIdAndDeleted(Long id, boolean deleted);
    List<Cart> findAllByDeleted(boolean deleted);
}
