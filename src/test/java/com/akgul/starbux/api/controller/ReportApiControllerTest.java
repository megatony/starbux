package com.akgul.starbux.api.controller;

import com.akgul.starbux.entity.*;
import com.akgul.starbux.repository.OrderRepository;
import com.akgul.starbux.service.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportApiControllerTest {

    private HttpHeaders headers = new HttpHeaders();
    private TestRestTemplate restTemplate = new TestRestTemplate();

    @LocalServerPort
    private int port;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderRepository orderRepository;

    private String createApiUrl(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    public void shouldGetOrderAmountPerUser() {
        orderRepository.deleteAll();
        User customer = userService.getUserByUserName("customer");
        Cart cart = new Cart();
        List<CartItem> cartItems = new ArrayList<>();
        Product product = productService.getProductById(4L);
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(255);
        cartItem.setDrinkProduct(product);
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);
        cart.setUser(customer);

        cartService.saveCart(cart);
        orderService.createOrder(cart, customer);

        User admin = userService.getUserByUserName("admin");
        Cart adminCart = new Cart();
        List<CartItem> adminCartItems = new ArrayList<>();
        Product adminProduct = productService.getProductById(3L);
        CartItem adminCartItem = new CartItem();
        adminCartItem.setQuantity(188);
        adminCartItem.setDrinkProduct(adminProduct);
        adminCartItems.add(adminCartItem);
        adminCart.setCartItems(adminCartItems);
        adminCart.setUser(admin);

        cartService.saveCart(adminCart);
        orderService.createOrder(adminCart, admin);


        headers.setBasicAuth("admin", "admin");
        HttpEntity<?> httpEntity = new HttpEntity<>("", headers);
        ResponseEntity<String> cartResponse = restTemplate.exchange(createApiUrl("/report/orderamountperuser"), HttpMethod.GET, httpEntity, String.class);

        Assert.assertEquals("[{\"amount\":846.00,\"userId\":2},{\"amount\":573.75,\"userId\":1}]", cartResponse.getBody());
    }

    @Test
    public void shouldGetMostUsedToppingsForDrinks() {
        orderRepository.deleteAll();
        Product product = productService.getProductById(4L);

        User customer = userService.getUserByUserName("customer");
        Cart cart = cartService.createCart(customer, product, 255);

        Product sideProduct = productService.getProductById(5L);
        List<Product> sideProducts = new ArrayList<>();
        sideProducts.add(sideProduct);
        cart.getCartItems().get(0).setSideProducts(sideProducts);

        cartService.saveCart(cart);

        Cart cart1 = cartService.createCart(customer, product, 211);
        Product sideProduct1 = productService.getProductById(5L);
        List<Product> sideProducts1 = new ArrayList<>();
        sideProducts1.add(sideProduct1);
        cart.getCartItems().get(0).setSideProducts(sideProducts1);
        orderService.createOrder(cart, customer);

        cartService.saveCart(cart1);
        orderService.createOrder(cart1, customer);

        headers.setBasicAuth("admin", "admin");
        HttpEntity<?> httpEntity = new HttpEntity<>("", headers);
        ResponseEntity<String> cartResponse = restTemplate.exchange(createApiUrl("/report/mostusedsideproducts"), HttpMethod.GET, httpEntity, String.class);

        Assert.assertEquals("[{\"drinkProductName\":\"Tea\",\"sideProductName\":\"Milk\",\"usingCount\":255}]", cartResponse.getBody());

    }
}
