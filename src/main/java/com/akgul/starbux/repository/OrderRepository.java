package com.akgul.starbux.repository;

import com.akgul.starbux.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order getByIdAndDeleted(Long id, boolean deleted);
    List<Order> findAllByDeleted(boolean deleted);
}
