package com.akgul.starbux.entity;

import com.akgul.starbux.enums.DiscountType;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "DISCOUNT")
public class Discount extends StarbuxObject {

    @Column(name = "DISCOUNT_AMOUNT")
    private BigDecimal discountAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "DISCOUNT_TYPE")
    private DiscountType discountType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_ID")
    private Order order;
}
