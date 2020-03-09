package com.akgul.starbux.api.controller.response;

import com.akgul.starbux.entity.Order;
import com.akgul.starbux.enums.DiscountType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderApiResponse extends StarbuxApiResponse {
    @JsonIgnore
    private Order order;

    private Long orderId;
    private BigDecimal totalAmount;
    private BigDecimal amount;
    private BigDecimal discountAmount;
    private DiscountType discountType;

    public OrderApiResponse(Order order) {
        if (ObjectUtils.isEmpty(order)) {
            return;
        }
        this.order = order;
        this.orderId = order.getId();
        this.totalAmount = order.getTotalAmount();
        this.amount = order.getAmount();
        this.discountAmount = order.getDiscountAmount();

        if (!ObjectUtils.isEmpty(order.getDiscount())) {
            this.discountType = order.getDiscount().getDiscountType();
        }
    }
}
