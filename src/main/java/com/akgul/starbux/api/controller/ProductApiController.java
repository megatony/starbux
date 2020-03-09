package com.akgul.starbux.api.controller;

import com.akgul.starbux.api.controller.request.ProductApiRequest;
import com.akgul.starbux.api.controller.response.ProductApiResponse;
import com.akgul.starbux.api.controller.response.StarbuxApiResponse;
import com.akgul.starbux.api.controller.response.StarbuxNotFoundApiResponse;
import com.akgul.starbux.entity.Product;
import com.akgul.starbux.service.ProductService;
import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductApiController {

    @Autowired
    private ProductService productService;

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
    public StarbuxApiResponse addProduct(@RequestBody ProductApiRequest productApiRequest) {
        if (ObjectUtils.isEmpty(productApiRequest)) {
            return new StarbuxNotFoundApiResponse("Due to add product, please enter name, price and type (DRINK or SIDE)");
        }

        if (StringHelper.isEmpty(productApiRequest.getProductName())) {
            return new StarbuxNotFoundApiResponse("Product name should not be empty");
        }

        if (ObjectUtils.isEmpty(productApiRequest.getProductPrice()) || productApiRequest.getProductPrice().compareTo(BigDecimal.ZERO) <= 0) {
            return new StarbuxNotFoundApiResponse("Product price must be a number that bigger than 0.");
        }

        if (ObjectUtils.isEmpty(productApiRequest.getProductType())) {
            return new StarbuxNotFoundApiResponse("Product type is not valid. (Must be DRINK or SIDE)");
        }

        Product product = new ProductApiRequest().createProduct(productApiRequest);
        return new ProductApiResponse(productService.saveProduct(product));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/update/{productId}", method = RequestMethod.PUT)
    public StarbuxApiResponse updateProduct(@PathVariable(value = "productId") Long productId, @RequestBody ProductApiRequest productApiRequest) {
        Product product = productService.getProductById(productId);

        if (ObjectUtils.isEmpty(product)) {
            return new StarbuxNotFoundApiResponse("Product id " + productId + " not found");
        }

        if (ObjectUtils.isEmpty(productApiRequest)) {
            return new StarbuxNotFoundApiResponse("Due to update product, please enter name, price or type (DRINK or SIDE)");
        }

        if (!StringHelper.isEmpty(productApiRequest.getProductName())) {
            product.setProductName(productApiRequest.getProductName());
        }

        if (!ObjectUtils.isEmpty(productApiRequest.getProductPrice()) && productApiRequest.getProductPrice().compareTo(BigDecimal.ZERO) > 0) {
            product.setPrice(productApiRequest.getProductPrice());
        }

        if (!ObjectUtils.isEmpty(productApiRequest.getProductType())) {
            product.setProductType(productApiRequest.getProductType());
        }

        return new ProductApiResponse(productService.saveProduct(product));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/delete/{productId}", method = RequestMethod.DELETE)
    public StarbuxApiResponse deleteProduct(@PathVariable(value = "productId") Long productId) {
        Product product = productService.getProductById(productId);

        if (ObjectUtils.isEmpty(product)) {
            return new StarbuxNotFoundApiResponse("Product id " + productId + " not found");
        }

        product.setDeleted(true);

        ProductApiResponse response = new ProductApiResponse(productService.saveProduct(product));
        response.setMessage("Product is deleted");
        return response;
    }


}
