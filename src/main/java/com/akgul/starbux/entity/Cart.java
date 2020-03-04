package com.akgul.starbux.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "CART")
public class Cart extends StarbuxObject {
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(fetch = FetchType.EAGER)
    private List<CartItem> cartItem;

}
