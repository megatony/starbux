package com.akgul.starbux.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "ORDERS")
public class Order extends StarbuxObject {
    @OneToOne
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "TOTAL_AMOUNT")
    private BigDecimal totalAmount;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "DISCOUNT_AMOUNT")
    private BigDecimal discountAmount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DISCOUNT_ID")
    private Discount discount;
}
