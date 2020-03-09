package com.akgul.starbux.service;

import com.akgul.starbux.controller.CartController;
import com.akgul.starbux.entity.*;
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
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartController cartController;

    @Mock
    private DiscountService discountService;

    @Test
    public void shouldCreateCart() {
        User user = new User();
        user.setUserName("test");

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductName("Test product");
        product.setProductType(ProductType.DRINK);
        product.setPrice(BigDecimal.TEN);
        products.add(product);

        int purchaseCount = 1;

        Cart cart = new Cart();
        cart.setUser(user);
        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setQuantity(purchaseCount);
        cartItem.setProducts(products);
        cartItem.setCart(cart);
        cartItem.setPrice(BigDecimal.TEN);
        cartItems.add(cartItem);

        cart.setAmount(BigDecimal.TEN);
        cart.setTotalAmount(BigDecimal.TEN);
        cart.setCartItems(cartItems);

        when(cartController.saveCart(any())).thenReturn(cart);
        Cart finalCart = cartService.createCart(user, product, purchaseCount);

        Assert.assertNotNull(finalCart);
    }

    @Test
    public void shouldGetCartByUser() {
        User user = new User();
        user.setUserName("test");

        Cart cart = new Cart();
        cart.setUser(user);

        when(cartController.getCartByUser(user)).thenReturn(cart);

        Assert.assertEquals(cart, cartController.getCartByUser(user));
    }

    @Test
    public void shouldGetCartItemOfProductFromCartDirectlyWhenNoSideProductsExist() {
        User user = new User();
        user.setUserName("test");

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductName("Test product");
        product.setProductType(ProductType.DRINK);
        product.setPrice(BigDecimal.TEN);
        products.add(product);

        int purchaseCount = 1;

        Cart cart = new Cart();
        cart.setUser(user);
        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setQuantity(purchaseCount);
        cartItem.setProducts(products);
        cartItem.setCart(cart);
        cartItem.setPrice(BigDecimal.TEN);
        cartItems.add(cartItem);

        cart.setAmount(BigDecimal.TEN);
        cart.setTotalAmount(BigDecimal.TEN);
        cart.setCartItems(cartItems);

        Assert.assertEquals(cartService.getCartItemOfProductFromCart(cart, product), cartItem);
    }

    @Test
    public void shouldGetCartItemOfDrinkProductWithNoSideWhenProductWithSideAvailable() {
        User user = new User();
        user.setUserName("test");

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductName("Test product");
        product.setProductType(ProductType.DRINK);
        product.setPrice(BigDecimal.TEN);
        products.add(product);

        int purchaseCount = 1;

        Cart cart = new Cart();
        cart.setUser(user);

        List<CartItem> cartItems = new ArrayList<>();

        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setQuantity(purchaseCount);
        cartItem.setProducts(products);
        cartItem.setCart(cart);
        cartItem.setPrice(BigDecimal.TEN);
        cartItems.add(cartItem);

        Product sideProduct = new Product();
        sideProduct.setProductName("Side product");
        sideProduct.setProductType(ProductType.SIDE);
        sideProduct.setPrice(BigDecimal.ONE);

        CartItem cartItemWithSideProduct = new CartItem();
        cartItemWithSideProduct.setId(2L);
        cartItemWithSideProduct.setQuantity(purchaseCount);

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(sideProduct);

        cartItemWithSideProduct.setProducts(productList);
        cartItemWithSideProduct.setCart(cart);
        cartItemWithSideProduct.setPrice(BigDecimal.valueOf(11));

        cart.setAmount(BigDecimal.valueOf(11));
        cart.setTotalAmount(BigDecimal.valueOf(11));
        cartItems.add(cartItemWithSideProduct);
        cart.setCartItems(cartItems);

        Assert.assertEquals(cartService.getCartItemOfProductFromCart(cart, product), cartItem);
    }

    @Test
    public void shouldNotApplyAnyDiscountWhenDiscountIsNotAvailable() {
        Cart cart = new Cart();
        cart.setTotalAmount(BigDecimal.ONE);

        when(discountService.selectDiscountForCart(cart)).thenReturn(null);

        Assert.assertEquals(cart, cartService.applyDiscountForCart(cart));
    }

    @Test
    public void shouldApplyDiscountToCartWhenItsAvailable() {
        Cart cart = new Cart();
        cart.setTotalAmount(BigDecimal.valueOf(12));

        Discount discount = new Discount();
        discount.setDiscountType(DiscountType.PercentageBased);
        discount.setDiscountAmount(BigDecimal.valueOf(3));

        when(discountService.selectDiscountForCart(cart)).thenReturn(discount);

        Assert.assertEquals(discount, cartService.applyDiscountForCart(cart).getDiscount());
    }

    @Test
    public void shouldVerifyPrepareCartValuesAndApplyDiscountBeforeSavingCart() {
        Cart cart = new Cart();
        cart.setTotalAmount(BigDecimal.valueOf(12));

        Discount discount = new Discount();
        discount.setDiscountType(DiscountType.PercentageBased);
        discount.setDiscountAmount(BigDecimal.valueOf(3));
        when(discountService.selectDiscountForCart(cart)).thenReturn(discount);
        cartService.saveCart(cart);
        verify(cartController, times(1)).saveCart(cart);
    }

    @Test
    public void shouldVerifyDeleteProcessHandlingWithSoftDelete() {
        Cart cart = new Cart();
        cart.setTotalAmount(BigDecimal.valueOf(12));

        Discount discount = new Discount();
        discount.setDiscountType(DiscountType.PercentageBased);
        discount.setDiscountAmount(BigDecimal.valueOf(3));

        cartService.deleteCart(cart);
        verify(cartController, times(1)).saveCart(cart);
    }
}
