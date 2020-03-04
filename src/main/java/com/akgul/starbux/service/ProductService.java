package com.akgul.starbux.service;

import com.akgul.starbux.controller.ProductController;
import com.akgul.starbux.entity.Product;
import com.akgul.starbux.enums.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductController productController;

    public Product getProductById(Long productId) {
        return productController.getProductById(productId);
    }

    public List<Product> getProducts() {
        return productController.getProducts();
    }

    public List<Product> getDrinkProducts() {
        return productController.getProductsByProductType(ProductType.DRINK);
    }

    public List<Product> getSideProducts() {
        return productController.getProductsByProductType(ProductType.SIDE);
    }
}
