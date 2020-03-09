package com.akgul.starbux.service;

import com.akgul.starbux.controller.DiscountController;
import com.akgul.starbux.entity.Cart;
import com.akgul.starbux.entity.CartItem;
import com.akgul.starbux.entity.Discount;
import com.akgul.starbux.entity.Product;
import com.akgul.starbux.enums.DiscountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class DiscountService {
    private static final BigDecimal RATE_DISCOUNT_VALUE = BigDecimal.valueOf(0.25D);
    private static final BigDecimal RATE_DISCOUNT_CART_LIMIT = BigDecimal.valueOf(12);
    private static final int LOWEST_AMOUNT_DISCOUNT_LIMIT = 3;

    @Autowired
    private DiscountController discountController;

    @Autowired
    private ProductService productService;

    public Discount saveDiscount(Discount discount) {
        return discountController.saveDiscount(discount);
    }

    public Discount selectDiscountForCart(Cart cart) {
        BigDecimal rateDiscountAmount = getRateDiscountAmountForCart(cart);
        BigDecimal amountDiscountAmount = getDiscountByLowestAmountForCart(cart);

        if (rateDiscountAmount.compareTo(BigDecimal.ZERO) == 0 && amountDiscountAmount.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }

        if (rateDiscountAmount.compareTo(amountDiscountAmount) > 0) {
            Discount rateDiscount = new Discount();
            rateDiscount.setDiscountAmount(rateDiscountAmount);
            rateDiscount.setDiscountType(DiscountType.PercentageBased);
            return rateDiscount;
        }

        Discount amountDiscount = new Discount();
        amountDiscount.setDiscountAmount(amountDiscountAmount);
        amountDiscount.setDiscountType(DiscountType.AmountBased);
        return amountDiscount;
    }

    public BigDecimal getRateDiscountAmountForCart(Cart cart) {
        if (cart.getTotalAmount().compareTo(RATE_DISCOUNT_CART_LIMIT) == -1) {
            return BigDecimal.ZERO;
        }

        return cart.getTotalAmount().multiply(RATE_DISCOUNT_VALUE);
    }

    public BigDecimal getDiscountByLowestAmountForCart(Cart cart) {
        if (cart.calculateTotalDrinkProductNumber() < LOWEST_AMOUNT_DISCOUNT_LIMIT) {
            return BigDecimal.ZERO;
        }
        List<Product> products = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            products.addAll(cartItem.getProducts());
        }
        return productService.getCheapestProductFromProductList(products).getPrice();
    }
}
