package com.akgul.starbux.repository;

import com.akgul.starbux.entity.Order;
import com.akgul.starbux.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order getByIdAndDeleted(Long id, boolean deleted);
    Order getByUserEqualsAndIdAndDeleted(User user, Long id, boolean deleted);
    List<Order> findAllByDeleted(boolean deleted);
    List<Order> findAllByUserEqualsAndDeleted(User user, boolean deleted);
}
