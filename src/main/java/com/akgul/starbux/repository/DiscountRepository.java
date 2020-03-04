package com.akgul.starbux.repository;

import com.akgul.starbux.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Discount getByIdAndDeleted(Long id, boolean deleted);
    List<Discount> findAllByDeleted(boolean deleted);
}
