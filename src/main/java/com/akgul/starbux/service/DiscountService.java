package com.akgul.starbux.service;

import com.akgul.starbux.controller.DiscountController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {
    
    @Autowired
    private DiscountController discountController;
}
