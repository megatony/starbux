package com.akgul.starbux.service;

import com.akgul.starbux.controller.ProductController;
import com.akgul.starbux.entity.Product;
import com.akgul.starbux.enums.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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

    public Product getCheapestProductFromProductList(List<Product> products) {
        return products.stream().sorted(new Comparator<Product>() {
            @Override
            public int compare(Product product, Product t1) {
                return product.getPrice().compareTo(t1.getPrice());
            }
        }).findFirst().get();
    }

    public Product saveProduct(Product product) {
        return productController.saveProduct(product);
    }
}
