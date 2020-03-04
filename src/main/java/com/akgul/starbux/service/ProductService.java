package com.akgul.starbux.service;

import com.akgul.starbux.controller.ProductController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductController productController;
}
