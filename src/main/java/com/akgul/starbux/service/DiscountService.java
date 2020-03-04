package com.akgul.starbux.service;

import com.akgul.starbux.controller.DiscountController;
import com.akgul.starbux.entity.Discount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountService {
    
    @Autowired
    private DiscountController discountController;

    public Discount getDiscountById(Long discountId) {
        return discountController.getDiscountById(discountId);
    }

    public List<Discount> getDiscounts() {
        return discountController.getDiscounts();
    }
}
