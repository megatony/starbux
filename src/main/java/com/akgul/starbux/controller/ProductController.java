package com.akgul.starbux.controller;

import com.akgul.starbux.entity.Product;
import com.akgul.starbux.enums.ProductType;
import com.akgul.starbux.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository repository;

    public Product getProductById(Long productId) {
        return repository.getByIdAndDeleted(productId, false);
    }

    public List<Product> getProducts() {
        return repository.findAllByDeleted(false);
    }

    public List<Product> getProductsByProductType(ProductType productType) {
        return repository.findAllByDeletedAndProductTypeEquals(false, productType);
    }


}
