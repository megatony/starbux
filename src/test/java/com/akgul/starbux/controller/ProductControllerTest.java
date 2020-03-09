package com.akgul.starbux.controller;

import com.akgul.starbux.entity.Product;
import com.akgul.starbux.repository.ProductRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductRepository repository;

    @Test
    public void shouldGetNotDeletedProducts() {
        Product product = new Product();
        product.setDeleted(true);

        Product product1 = new Product();

        Product product2 = new Product();
        Product product3 = new Product();
        product3.setDeleted(true);

        Product product4 = new Product();

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product4);

        when(repository.findAllByDeleted(false)).thenReturn(products);

        Assert.assertEquals(productController.getProducts(), products);
    }
}
