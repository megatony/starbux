package com.akgul.starbux.api.controller;

import com.akgul.starbux.api.controller.request.ProductApiRequest;
import com.akgul.starbux.entity.Product;
import com.akgul.starbux.enums.ProductType;
import com.akgul.starbux.service.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private ProductService productService;

    private HttpHeaders headers = new HttpHeaders();
    private TestRestTemplate restTemplate = new TestRestTemplate();

    private String createApiUrl(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    public void shouldGetProductList() {
        headers.setBasicAuth("customer", "customer");
        HttpEntity<?> httpEntity = new HttpEntity<>("", headers);
        ResponseEntity<List<Product>> cartResponse = restTemplate.exchange(createApiUrl("/product"), HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<Product>>() {});
        Assert.assertEquals(cartResponse.getBody().get(0).getProductName(), "Black Coffee");
    }

    @Test
    public void shouldGetProductById() {
        headers.setBasicAuth("customer", "customer");
        HttpEntity<?> httpEntity = new HttpEntity<>("", headers);
        ResponseEntity<Product> cartResponse = restTemplate.exchange(createApiUrl("/product/2"), HttpMethod.GET, httpEntity, Product.class);
        Assert.assertEquals(cartResponse.getBody().getProductName(), "Latte");
    }

    @Test
    public void shouldAddProduct() {
        headers.setBasicAuth("admin", "admin");
        headers.setContentType(MediaType.APPLICATION_JSON);
        ProductApiRequest apiRequest = new ProductApiRequest();
        apiRequest.setProductType(ProductType.DRINK);
        apiRequest.setProductPrice(BigDecimal.TEN);
        apiRequest.setProductName("Test product");
        HttpEntity<?> httpEntity = new HttpEntity<>(apiRequest, headers);
        ResponseEntity<Product> cartResponse = restTemplate.exchange(createApiUrl("/product/add"), HttpMethod.POST, httpEntity, Product.class);
        Assert.assertEquals(cartResponse.getBody().getProductName(), "Test product");
    }

    @Test
    public void shouldUpdateProduct() {
        Product product = new Product();
        product.setProductName("ExampleUpdate");
        product.setPrice(BigDecimal.TEN);
        product.setProductType(ProductType.DRINK);

        productService.saveProduct(product);

        headers.setBasicAuth("admin", "admin");
        headers.setContentType(MediaType.APPLICATION_JSON);
        ProductApiRequest apiRequest = new ProductApiRequest();
        apiRequest.setProductPrice(BigDecimal.valueOf(23.23));
        HttpEntity<?> httpEntity = new HttpEntity<>(apiRequest, headers);
        ResponseEntity<String> cartResponse = restTemplate.exchange(createApiUrl("/product/update/"+product.getId()), HttpMethod.PUT, httpEntity, String.class);
        Assert.assertTrue(cartResponse.getBody().contains("ExampleUpdate"));
        Assert.assertTrue(cartResponse.getBody().contains("23.23"));
    }

    @Test
    public void shouldDeleteProduct() {
        Product product = new Product();
        product.setProductName("Example");
        product.setPrice(BigDecimal.TEN);
        product.setProductType(ProductType.DRINK);
        productService.saveProduct(product);
        headers.setBasicAuth("admin", "admin");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> httpEntity = new HttpEntity<>("", headers);
        ResponseEntity<String> cartResponse = restTemplate.exchange(createApiUrl("/product/delete/"+product.getId()), HttpMethod.DELETE, httpEntity, String.class);
        Assert.assertTrue(cartResponse.getBody().contains("Example"));
        Assert.assertTrue(cartResponse.getBody().contains("Product is deleted"));
    }
}
