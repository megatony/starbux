package com.akgul.starbux.api.controller;

import com.akgul.starbux.api.controller.request.ProductApiRequest;
import com.akgul.starbux.api.controller.response.ProductApiResponse;
import com.akgul.starbux.api.controller.response.StarbuxApiResponse;
import com.akgul.starbux.api.controller.response.StarbuxNotFoundApiResponse;
import com.akgul.starbux.entity.CartItem;
import com.akgul.starbux.entity.Product;
import com.akgul.starbux.entity.User;
import com.akgul.starbux.enums.UserType;
import com.akgul.starbux.service.CartItemService;
import com.akgul.starbux.service.CartService;
import com.akgul.starbux.service.ProductService;
import com.akgul.starbux.service.UserService;
import org.hibernate.internal.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductApiController {

    private Logger LOGGER = LoggerFactory.getLogger(ProductApiController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private CartService cartService;

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
    public @ResponseBody
    List<ProductApiResponse> getProductList() {
        List<ProductApiResponse> productApiResponses = new ArrayList<>();
        for (Product product : productService.getProducts()) {
            productApiResponses.add(new ProductApiResponse(product));
        }
        return productApiResponses;
    }

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    public StarbuxApiResponse getProduct(@PathVariable(value = "productId") Long productId) {
        Product product = productService.getProductById(productId);

        if (ObjectUtils.isEmpty(product)) {
            return new StarbuxNotFoundApiResponse("Product id " + productId + " not found");
        }

        return new ProductApiResponse(product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public StarbuxApiResponse addProduct(Authentication authentication, @RequestBody ProductApiRequest productApiRequest) {
        User user = userService.getUserByUserName(authentication.getName());

        if (ObjectUtils.isEmpty(user) || !user.getUserType().equals(UserType.ADMIN)) {
            return new StarbuxNotFoundApiResponse("Admin user not found.");
        }

        if (ObjectUtils.isEmpty(productApiRequest)) {
            return new StarbuxNotFoundApiResponse("Due to add product, please enter name, price and type (DRINK or SIDE).");
        }

        if (StringHelper.isEmpty(productApiRequest.getProductName())) {
            return new StarbuxNotFoundApiResponse("Product name should not be empty.");
        }

        if (ObjectUtils.isEmpty(productApiRequest.getProductPrice()) || productApiRequest.getProductPrice().compareTo(BigDecimal.ZERO) <= 0) {
            return new StarbuxNotFoundApiResponse("Product price must be a number that bigger than 0.");
        }

        if (ObjectUtils.isEmpty(productApiRequest.getProductType())) {
            return new StarbuxNotFoundApiResponse("Product type is not valid. (Must be DRINK or SIDE).");
        }

        Product product = productApiRequest.createProduct(productApiRequest);
        return new ProductApiResponse(productService.saveProduct(product));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/update/{productId}", method = RequestMethod.PUT)
    public StarbuxApiResponse updateProduct(Authentication authentication, @PathVariable(value = "productId") Long productId, @RequestBody ProductApiRequest productApiRequest) {
        User user = userService.getUserByUserName(authentication.getName());

        if (ObjectUtils.isEmpty(user) || !user.getUserType().equals(UserType.ADMIN)) {
            return new StarbuxNotFoundApiResponse("Admin user not found.");
        }

        Product product = productService.getProductById(productId);

        if (ObjectUtils.isEmpty(product)) {
            return new StarbuxNotFoundApiResponse("Product id " + productId + " not found");
        }

        List<CartItem> updatedProductCartItems = cartItemService.getCartItemsByProduct(product);

        if (ObjectUtils.isEmpty(productApiRequest)) {
            return new StarbuxNotFoundApiResponse("Due to update product, please enter name, price or type (DRINK or SIDE)");
        }

        if (!StringHelper.isEmpty(productApiRequest.getProductName())) {
            LOGGER.debug("Product " + product.getId() + " name will be updated as " + productApiRequest.getProductName());
            product.setProductName(productApiRequest.getProductName());
        }

        if (!ObjectUtils.isEmpty(productApiRequest.getProductType())) {
            LOGGER.debug("Product " + product.getId() + " type will be updated as " + productApiRequest.getProductType());
            product.setProductType(productApiRequest.getProductType());
        }

        if (!ObjectUtils.isEmpty(productApiRequest.getProductPrice()) && productApiRequest.getProductPrice().compareTo(BigDecimal.ZERO) > 0) {
            LOGGER.debug("Product " + product.getId() + " price will be updated as " + productApiRequest.getProductPrice());
            product.setPrice(productApiRequest.getProductPrice());
            productService.saveProduct(product);

            if (!CollectionUtils.isEmpty(updatedProductCartItems)) {
                cartItemService.deleteCartItems(updatedProductCartItems).forEach(item -> cartService.saveCart(item.getCart()));
                LOGGER.info("After product price update, all active carts contains product id " + product.getId() + " is deleted.");
            }
            return new ProductApiResponse(product);
        }

        return new ProductApiResponse(productService.saveProduct(product));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/delete/{productId}", method = RequestMethod.DELETE)
    public StarbuxApiResponse deleteProduct(Authentication authentication, @PathVariable(value = "productId") Long productId) {
        User user = userService.getUserByUserName(authentication.getName());

        if (ObjectUtils.isEmpty(user) || !user.getUserType().equals(UserType.ADMIN)) {
            return new StarbuxNotFoundApiResponse("Admin user not found.");
        }

        Product product = productService.getProductById(productId);

        if (ObjectUtils.isEmpty(product)) {
            return new StarbuxNotFoundApiResponse("Product id " + productId + " not found");
        }

        List<CartItem> deletedProductCartItems = cartItemService.getCartItemsByProduct(product);

        if (!CollectionUtils.isEmpty(deletedProductCartItems)) {
            cartItemService.deleteCartItems(deletedProductCartItems);

            for (CartItem cartItem : deletedProductCartItems) {
                cartService.saveCart(cartItem.getCart());
            }
        }

        product.setDeleted(true);

        ProductApiResponse response = new ProductApiResponse(productService.saveProduct(product));

        String productDeleteMessage = "Product is deleted";
        response.setMessage(productDeleteMessage);

        LOGGER.info(productDeleteMessage + " id " + product.getId());
        LOGGER.info("After product update, all active carts contains product id " + product.getId() + " is deleted.");

        return response;
    }


}
