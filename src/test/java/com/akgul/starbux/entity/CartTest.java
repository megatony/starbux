package com.akgul.starbux.entity;

import com.akgul.starbux.enums.DiscountType;
import com.akgul.starbux.enums.ProductType;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartTest {

    @Test
    public void shouldCalculateTotalDrinkProductNumber() {
        Cart cart = new Cart();

        Product product = new Product();
        product.setProductType(ProductType.DRINK);
        product.setPrice(BigDecimal.TEN);

        Product product1 = new Product();
        product1.setProductType(ProductType.DRINK);
        product1.setPrice(BigDecimal.TEN);

        Product product2 = new Product();
        product2.setProductType(ProductType.DRINK);
        product2.setPrice(BigDecimal.TEN);

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(3);
        cartItem.setProducts(new ArrayList<>());
        cartItem.setDrinkProduct(product);

        CartItem cartItem2 = new CartItem();
        cartItem2.setQuantity(6);
        cartItem2.setProducts(new ArrayList<>());
        cartItem2.setDrinkProduct(product);

        CartItem cartItem3 = new CartItem();
        cartItem3.setQuantity(9);
        cartItem3.setProducts(new ArrayList<>());
        cartItem3.setDrinkProduct(product);

        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem);
        cartItems.add(cartItem2);
        cartItems.add(cartItem3);

        cart.setCartItems(cartItems);

        Assert.assertEquals(cart.calculateTotalDrinkProductNumber(), 18);
    }

    @Test
    public void shouldGetCartItemPrices() {
        Cart cart = new Cart();

        Product product = new Product();
        product.setProductType(ProductType.DRINK);
        product.setPrice(BigDecimal.TEN);

        Product product1 = new Product();
        product1.setProductType(ProductType.DRINK);
        product1.setPrice(BigDecimal.TEN);

        Product product2 = new Product();
        product2.setProductType(ProductType.DRINK);
        product2.setPrice(BigDecimal.TEN);

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(3);
        cartItem.setProducts(new ArrayList<>());
        cartItem.setDrinkProduct(product);

        CartItem cartItem2 = new CartItem();
        cartItem2.setQuantity(6);
        cartItem2.setProducts(new ArrayList<>());
        cartItem2.setDrinkProduct(product);

        CartItem cartItem3 = new CartItem();
        cartItem3.setQuantity(9);
        cartItem3.setProducts(new ArrayList<>());
        cartItem3.setDrinkProduct(product);

        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem);
        cartItems.add(cartItem2);
        cartItems.add(cartItem3);

        cart.setCartItems(cartItems);

        Assert.assertEquals(cart.getCartItemPrices().toString(), "180");
    }

    @Test
    public void shouldAddCartItem() {
        Cart cart = new Cart();
        CartItem cartItem = new CartItem();
        cart.addCartItem(cartItem);
        Assert.assertEquals(cart.getCartItems().size(), 1);
    }

    @Test
    public void shouldSetDiscountAmount() {
        Cart cart = new Cart();
        cart.setTotalAmount(BigDecimal.valueOf(100));

        Discount discount = new Discount();
        discount.setDiscountType(DiscountType.AmountBased);
        discount.setDiscountAmount(BigDecimal.valueOf(25));
        cart.setDiscount(discount);
        Assert.assertEquals(cart.getAmount().toString(), BigDecimal.valueOf(75).toString());
    }
}
