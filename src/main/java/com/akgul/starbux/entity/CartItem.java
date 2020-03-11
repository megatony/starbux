package com.akgul.starbux.entity;

import com.akgul.starbux.enums.ProductType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "CART_ITEM")
public class CartItem extends StarbuxObject {

    @Column(name = "QUANTITY")
    private int quantity;

    @Column(name = "PRICE")
    private BigDecimal price = BigDecimal.ZERO;

    @OneToOne
    private Product drinkProduct;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Product> sideProducts = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "CART_ID")
    private Cart cart;

    public void calculatePrice() {
        BigDecimal finalPrice = getDrinkProduct().getPrice().multiply(BigDecimal.valueOf(quantity));
        for (Product product : sideProducts) {
            finalPrice = finalPrice.add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        }
        price = finalPrice;
    }

    public void increaseQuantity(int increasedAmount) {
        quantity += increasedAmount;
    }
}
