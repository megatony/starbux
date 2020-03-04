package com.akgul.starbux.repository;

import com.akgul.starbux.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product getByIdAndDeleted(Long id, boolean deleted);
    List<Product> findAllByDeleted(boolean deleted);
}
