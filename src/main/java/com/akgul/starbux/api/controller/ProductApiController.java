package com.akgul.starbux.api.controller;

import com.akgul.starbux.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductApiController {

    @Autowired
    private ProductService productService;

}
