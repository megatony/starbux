package com.akgul.starbux.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "CART")
public class Cart extends StarbuxObject {
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Where(clause = "DELETED=0")
    private List<CartItem> cartItems = new ArrayList<>();

    @Column(name = "TOTAL_AMOUNT")
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(name = "AMOUNT")
    private BigDecimal amount = BigDecimal.ZERO;

    @Column(name = "DISCOUNT_AMOUNT")
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Transient
    private Discount discount;

    public void prepareValues() {
        setTotalAmount(getCartItemPrices());

        if (ObjectUtils.isEmpty(discount)) {
            setAmount(totalAmount);
            setDiscountAmount(BigDecimal.ZERO);
        }

        calculateTotalDrinkProductNumber();
    }

    public int calculateTotalDrinkProductNumber() {
        int totalDrinkProductNumber = 0;
        for (CartItem cartItem : cartItems) {
            if (ObjectUtils.isEmpty(cartItem.getDrinkProduct())) {
                continue;
            }
            totalDrinkProductNumber += cartItem.getQuantity();
        }
        return totalDrinkProductNumber;
    }

    public BigDecimal getCartItemPrices() {
        totalAmount = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            cartItem.calculatePrice();
            totalAmount = totalAmount.add(cartItem.getPrice());
        }
        return totalAmount;
    }

    public void addCartItem(CartItem cartItem) {
        if (ObjectUtils.isEmpty(cartItems)) {
            cartItems = new ArrayList<>();
        }
        this.cartItems.add(cartItem);
    }

    public void setDiscount(Discount discount) {
        if (ObjectUtils.isEmpty(discount)) {
            return;
        }
        this.discount = discount;
        setDiscountAmount(discount.getDiscountAmount());
        setAmount(getTotalAmount().subtract(discount.getDiscountAmount()));
    }

}
