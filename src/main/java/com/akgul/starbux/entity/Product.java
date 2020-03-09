package com.akgul.starbux.entity;

import com.akgul.starbux.enums.ProductType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "PRODUCT")
public class Product extends StarbuxObject {

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRODUCT_TYPE")
    private ProductType productType = ProductType.DRINK;
}
