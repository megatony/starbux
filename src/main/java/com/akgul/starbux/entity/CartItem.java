package com.akgul.starbux.entity;

import com.akgul.starbux.enums.ProductType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "CART_ID")
    private Cart cart;

    public void calculatePrice() {
        BigDecimal finalPrice = BigDecimal.ZERO;
        for (Product product : products) {
            finalPrice = finalPrice.add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        }
        price = finalPrice;
    }

    public Product getDrinkProduct() {
        for (Product product : products) {
            if (ProductType.DRINK.equals(product.getProductType())) {
                return product;
            }
        }
        return null;
    }

    public List<Product> getSideProducts() {
        ArrayList<Product> sideProducts = new ArrayList<>();
        for (Product product : products) {
            if (ProductType.SIDE.equals(product.getProductType())) {
                sideProducts.add(product);
            }
        }
        return sideProducts;
    }

    public void setDrinkProduct(Product product) {
        products.add(product);
        calculatePrice();
    }

    public void increaseQuantity(int increasedAmount) {
        quantity += increasedAmount;
        calculatePrice();
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        calculatePrice();
    }

    public void addProduct(Product product) {
        if (!ObjectUtils.isEmpty(this.products)) {
            this.products.add(product);
            calculatePrice();
        }
    }

    public void removeProduct(Product product) {
        if (!ObjectUtils.isEmpty(this.products)) {
            this.products.remove(product);
            calculatePrice();
        }
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        calculatePrice();
    }
}
