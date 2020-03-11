package com.akgul.starbux.api.controller;

import com.akgul.starbux.api.controller.response.*;
import com.akgul.starbux.entity.Cart;
import com.akgul.starbux.entity.CartItem;
import com.akgul.starbux.entity.Product;
import com.akgul.starbux.entity.User;
import com.akgul.starbux.enums.ProductType;
import com.akgul.starbux.service.CartItemService;
import com.akgul.starbux.service.CartService;
import com.akgul.starbux.service.ProductService;
import com.akgul.starbux.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
@RestController
@RequestMapping("/cart")
public class CartApiController {

    private Logger LOGGER = LoggerFactory.getLogger(CartApiController.class);

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public @ResponseBody
    StarbuxApiResponse getCart(Authentication authentication) {
        User user = userService.getUserByUserName(authentication.getName());
        Cart cart = cartService.getCartByUser(user);

        if (ObjectUtils.isEmpty(cart)) {
            LOGGER.debug("Cart is null for user " + authentication.getName() + ", new cart created by default.");
            cart = new Cart();
            cart.setUser(user);
            return new CartApiResponse(cartService.saveCart(cart));
        }

        return new CartApiResponse(cart);
    }

    @RequestMapping(value = "/item/{cartItemId}", method = RequestMethod.GET)
    public @ResponseBody
    StarbuxApiResponse getCartItem(Authentication authentication, @PathVariable(value = "cartItemId") Long cartItemId) {
        User user = userService.getUserByUserName(authentication.getName());
        Cart cart = cartService.getCartByUser(user);

        if (ObjectUtils.isEmpty(cart)) {
            return new StarbuxNotFoundApiResponse("No active cart found for user. Please start with adding a product to your cart.");
        }

        CartItem cartItem = cartItemService.getCartItemById(cartItemId);

        if (ObjectUtils.isEmpty(cartItem)) {
            return new StarbuxNotFoundApiResponse("Cart item " + cartItemId + " not found");
        }

        return new CartItemApiResponse(cartItem);
    }

    @RequestMapping(value = "/add/{drinkProductId}/{purchaseCount}", method = RequestMethod.POST)
    public @ResponseBody
    StarbuxApiResponse addProductToCart(Authentication authentication, @PathVariable(value = "drinkProductId") Long productId, @PathVariable(value = "purchaseCount") int purchaseCount) {
        Product product = productService.getProductById(productId);

        if (ObjectUtils.isEmpty(product)) {
            return new StarbuxNotFoundApiResponse("Product id " + productId + " not found.");
        }

        if (ProductType.SIDE.equals(product.getProductType())) {
            return new StarbuxMethodNotAllowedApiResponse("You cannot add side product from this method.");
        }

        if (purchaseCount <= 0) {
            return new StarbuxNotFoundApiResponse("Purchase count must be bigger than 0.");
        }

        User user = userService.getUserByUserName(authentication.getName());
        Cart cart = cartService.getCartByUser(user);

        if (ObjectUtils.isEmpty(cart)) {
            return new CartApiResponse(cartService.createCart(user, product, purchaseCount));
        }

        CartItem cartItem = cartService.getCartItemOfDrinkProductFromCart(cart, product);

        if (ObjectUtils.isEmpty(cartItem) || cartItem.getSideProducts().size() != 0) {
            cart.addCartItem(cartItemService.createCartItem(cart, product, purchaseCount));
            LOGGER.debug("A new cart item created for user " + authentication.getName());
            cartService.saveCart(cart);
            return new CartApiResponse(cart);
        }

        cartItem.increaseQuantity(purchaseCount);
        cartService.saveCart(cart);

        return new CartApiResponse(cart);
    }

    @RequestMapping(value = "/add/side/{sideProductId}/{cartItemId}", method = RequestMethod.POST)
    public @ResponseBody
    StarbuxApiResponse addSideProductToCart(Authentication authentication, @PathVariable(value = "sideProductId") Long productId, @PathVariable(value = "cartItemId") Long cartItemId) {
        Product product = productService.getProductById(productId);

        if (ObjectUtils.isEmpty(product)) {
            return new StarbuxNotFoundApiResponse("Product id " + productId + " not found.");
        }

        if (ProductType.DRINK.equals(product.getProductType())) {
            return new StarbuxMethodNotAllowedApiResponse("You cannot add a drink product from this method.");
        }

        User user = userService.getUserByUserName(authentication.getName());
        Cart cart = cartService.getCartByUser(user);

        if (ObjectUtils.isEmpty(cart)) {
            return new StarbuxNotFoundApiResponse("No active cart found for user. Please start with adding a product to your cart.");
        }

        CartItem cartItem = cartItemService.getCartItemById(cartItemId);

        if (ObjectUtils.isEmpty(cartItem)) {
            return new StarbuxNotFoundApiResponse("Cart item id " + cartItemId + " not found.");
        }

        List<Product> cartItemProducts = cartItem.getSideProducts();

        if (cartItemProducts.contains(product)) {
            return new StarbuxConflictApiResponse(product.getProductName() + " is previously added to " + cartItem.getDrinkProduct().getProductName() + " drink.");
        }

        cartItem.getSideProducts().add(product);
        cartItemService.saveCartItem(cartItem);
        return new CartApiResponse(cartService.saveCart(cart));
    }

    @RequestMapping(value = "/update/{productId}/{cartItemId}/{purchaseCount}", method = RequestMethod.PUT)
    public @ResponseBody
    StarbuxApiResponse updateProductQuantity(Authentication authentication, @PathVariable(value = "productId") Long productId, @PathVariable(value = "cartItemId") Long cartItemId, @PathVariable(value = "purchaseCount") int purchaseCount) {
        Product product = productService.getProductById(productId);

        if (ObjectUtils.isEmpty(product)) {
            return new StarbuxNotFoundApiResponse("Product id " + productId + " not found.");
        }

        if (product.getProductType().equals(ProductType.SIDE)) {
            return new StarbuxMethodNotAllowedApiResponse("This method is only for drink products.");
        }

        if (purchaseCount <= 0) {
            return new StarbuxConflictApiResponse("Purchase count must be bigger than 0.");
        }

        User user = userService.getUserByUserName(authentication.getName());
        Cart cart = cartService.getCartByUser(user);

        if (ObjectUtils.isEmpty(cart)) {
            return new StarbuxNotFoundApiResponse("No active cart found for user. Please start with adding a product to your cart.");
        }

        CartItem cartItem = cartItemService.getCartItemById(cartItemId);

        if (ObjectUtils.isEmpty(cartItem)) {
            return new StarbuxNotFoundApiResponse("Cart item id " + cartItemId + " not found.");
        }

        cartItem.setQuantity(purchaseCount);
        cartService.saveCart(cart);

        return new CartApiResponse(cart);
    }

    @RequestMapping(value = "/delete/item/{cartItemId}", method = RequestMethod.DELETE)
    public @ResponseBody
    StarbuxApiResponse deleteCartItem(Authentication authentication, @PathVariable(value = "cartItemId") Long cartItemId) {
        User user = userService.getUserByUserName(authentication.getName());
        Cart cart = cartService.getCartByUser(user);

        if (ObjectUtils.isEmpty(cart)) {
            return new StarbuxNotFoundApiResponse("No active cart found for user. Please start with adding a product to your cart.");
        }

        CartItem cartItem = cartItemService.getCartItemById(cartItemId);

        if (ObjectUtils.isEmpty(cartItem)) {
            return new StarbuxNotFoundApiResponse("Cart item id " + cartItemId + " not found");
        }

        cartItem.setDeleted(true);
        cartItemService.saveCartItem(cartItem);
        cart.getCartItems().remove(cartItem);
        cartService.saveCart(cart);

        CartApiResponse apiResponse = new CartApiResponse(cartItem.getCart());
        String deleteMessage = "Cart item " + cartItemId + " is deleted.";
        LOGGER.debug(deleteMessage);
        apiResponse.setMessage(deleteMessage);
        return apiResponse;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public @ResponseBody
    StarbuxApiResponse deleteCart(Authentication authentication) {
        User user = userService.getUserByUserName(authentication.getName());

        if (ObjectUtils.isEmpty(user)) {
            return new StarbuxNotFoundApiResponse("User not found.");
        }

        Cart cart = cartService.getCartByUser(user);

        if (ObjectUtils.isEmpty(cart)) {
            return new StarbuxNotFoundApiResponse("There is no active cart for user. Start with adding a product.");
        }

        cart.setDeleted(true);
        cartItemService.deleteCartItems(cart.getCartItems());
        CartApiResponse apiResponse = new CartApiResponse(cartService.saveCart(cart));

        String cartDeleteMessage = "Cart " + cart.getId() + " is deleted.";
        LOGGER.debug(cartDeleteMessage);
        apiResponse.setMessage(cartDeleteMessage);

        return apiResponse;
    }

    @RequestMapping(value = "/delete/{cartItemId}/{productId}", method = RequestMethod.DELETE)
    public @ResponseBody
    StarbuxApiResponse deleteProductFromCart(Authentication authentication, @PathVariable(value = "cartItemId") Long cartItemId, @PathVariable(value = "productId") Long productId) {
        Product product = productService.getProductById(productId);

        if (ObjectUtils.isEmpty(product)) {
            return new StarbuxNotFoundApiResponse("Product " + productId + " not found.");
        }

        User user = userService.getUserByUserName(authentication.getName());
        CartItem cartItem = cartItemService.getCartItemById(cartItemId);

        if (ObjectUtils.isEmpty(cartItem)) {
            return new StarbuxNotFoundApiResponse("Cart item " + cartItemId + " is not found.");
        }

        Cart cart = cartService.getCartByUser(user);

        if (product.getProductType().equals(ProductType.DRINK) && cartItem.getDrinkProduct().equals(product)) {
            LOGGER.debug("Drink product required for a cart, cart will be deleted.");
            cartItem.setDeleted(true);
            cart.getCartItems().remove(cartItem);
            cartItemService.saveCartItem(cartItem);
            cartService.saveCart(cart);

            return new CartApiResponse(cart);
        }

        List<Product> cartItemProducts = cartItem.getSideProducts();

        if (!cartItemProducts.contains(product)) {
            return new StarbuxNotFoundApiResponse(product.getProductName() + " is not found at this cart item.");
        }

        cartItem.getSideProducts().remove(product);

        cartItemService.saveCartItem(cartItem);
        cartService.saveCart(cart);

        return new CartApiResponse(cart);
    }
}
