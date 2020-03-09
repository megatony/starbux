package com.akgul.starbux.service;

import com.akgul.starbux.entity.Product;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Test
    public void shouldGetCheapestProduct() {
        Product product = new Product();
        product.setPrice(BigDecimal.valueOf(3.99));

        Product secondProduct = new Product();
        secondProduct.setPrice(BigDecimal.valueOf(1.99));

        Product thirdProduct = new Product();
        thirdProduct.setPrice(BigDecimal.valueOf(2));

        List<Product> products = new ArrayList<>();
        products.add(product);
        products.add(secondProduct);
        products.add(thirdProduct);

        Assert.assertEquals(secondProduct, productService.getCheapestProductFromProductList(products));
    }
}
