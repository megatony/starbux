package com.akgul.starbux.service;

import com.akgul.starbux.controller.DiscountController;
import com.akgul.starbux.entity.Cart;
import com.akgul.starbux.entity.CartItem;
import com.akgul.starbux.entity.Product;
import com.akgul.starbux.enums.DiscountType;
import com.akgul.starbux.enums.ProductType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DiscountServiceTest {

    @InjectMocks
    private DiscountService discountService;

    @Mock
    private ProductService productService;

    @Mock
    private DiscountController controller;

    @Test
    public void shouldGetRateDiscountAmountForCartWhenItsAvailable() {
        Cart cart = new Cart();
        cart.setTotalAmount(BigDecimal.valueOf(12));

        Assert.assertEquals(discountService.getRateDiscountAmountForCart(cart), BigDecimal.valueOf(3).setScale(2));
    }

    @Test
    public void shouldGetDiscountByLowestAmountForCart() {
        Cart cart = new Cart();

        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(1);
        cartItem.setPrice(BigDecimal.valueOf(5));

        Product product = new Product();
        product.setPrice(BigDecimal.valueOf(5));
        product.setProductType(ProductType.DRINK);
        product.setProductName("test");

        CartItem cartItem2 = new CartItem();
        cartItem2.setQuantity(1);
        cartItem2.setPrice(BigDecimal.valueOf(4));

        Product product2 = new Product();
        product2.setPrice(BigDecimal.valueOf(4));
        product2.setProductType(ProductType.DRINK);
        product2.setProductName("test 2");

        CartItem cartItem3 = new CartItem();
        cartItem3.setQuantity(1);
        cartItem3.setPrice(BigDecimal.valueOf(3));

        Product product3 = new Product();
        product3.setPrice(BigDecimal.valueOf(3));
        product3.setProductType(ProductType.DRINK);
        product3.setProductName("test 3");

        cartItem.setDrinkProduct(product);
        cartItem2.setDrinkProduct(product2);
        cartItem3.setDrinkProduct(product3);

        cartItems.add(cartItem);
        cartItems.add(cartItem2);
        cartItems.add(cartItem3);

        cart.setCartItems(cartItems);
        when(productService.getCheapestProductFromProductList(any())).thenReturn(product3);
        Assert.assertEquals(discountService.getDiscountByLowestAmountForCart(cart).toString(), "3");
    }

    @Test
    public void shouldNotSelectWhenDiscountAmountIsZero() {
        Cart cart = new Cart();
        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(1);
        cartItem.setPrice(BigDecimal.valueOf(5));

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setPrice(BigDecimal.valueOf(5));
        product.setProductType(ProductType.DRINK);
        product.setProductName("test");
        products.add(product);

        cart.setCartItems(cartItems);

        Assert.assertEquals(discountService.selectDiscountForCart(cart), null);
    }

    @Test
    public void shouldSelectBiggestAmountDiscount() {
        Cart cart = new Cart();

        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(1);
        cartItem.setPrice(BigDecimal.valueOf(5));

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setPrice(BigDecimal.valueOf(5));
        product.setProductType(ProductType.DRINK);
        product.setProductName("test");
        products.add(product);

        CartItem cartItem2 = new CartItem();
        cartItem2.setQuantity(1);
        cartItem2.setPrice(BigDecimal.valueOf(4));

        Product product2 = new Product();
        product2.setPrice(BigDecimal.valueOf(4));
        product2.setProductType(ProductType.DRINK);
        product2.setProductName("test 2");

        CartItem cartItem3 = new CartItem();
        cartItem3.setQuantity(1);
        cartItem3.setPrice(BigDecimal.valueOf(3));

        Product product3 = new Product();
        product3.setPrice(BigDecimal.valueOf(3));
        product3.setProductType(ProductType.DRINK);
        product3.setProductName("test 3");

        cartItem.setDrinkProduct(product);
        cartItem2.setDrinkProduct(product2);
        cartItem3.setDrinkProduct(product3);

        cartItems.add(cartItem);
        cartItems.add(cartItem2);
        cartItems.add(cartItem3);

        cart.setCartItems(cartItems);
        when(productService.getCheapestProductFromProductList(any())).thenReturn(product3);
        Assert.assertEquals(discountService.selectDiscountForCart(cart).getDiscountType(), DiscountType.AmountBased);
    }
}
