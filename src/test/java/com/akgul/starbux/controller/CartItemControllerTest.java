package com.akgul.starbux.controller;

import com.akgul.starbux.entity.CartItem;
import com.akgul.starbux.entity.Product;
import com.akgul.starbux.enums.ProductType;
import com.akgul.starbux.repository.CartItemRepository;
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
public class CartItemControllerTest {

    @InjectMocks
    private CartItemController controller;

    @Mock
    private CartItemRepository repository;

    @Test
    public void shouldGetCartItemById() {
        CartItem cartItem = new CartItem();
        cartItem.setId(1L);

        when(repository.findByIdAndDeleted(1L, false)).thenReturn(cartItem);

        Assert.assertEquals(controller.getCartItemById(1L), cartItem);
    }

    @Test
    public void shouldGetDrinkProductCartItemsByProduct() {
        Product product = new Product();
        product.setProductType(ProductType.DRINK);

        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItem.setDrinkProduct(product);
        cartItem.setId(1L);

        CartItem cartItem2 = new CartItem();
        cartItem.setDrinkProduct(product);
        cartItem.setId(2L);

        cartItems.add(cartItem);
        cartItems.add(cartItem2);

        when(repository.findByDrinkProductAndDeleted(product, false)).thenReturn(cartItems);

        Assert.assertEquals(controller.getDrinkProductCartItemsByProduct(product).size(), 2);
    }

}
