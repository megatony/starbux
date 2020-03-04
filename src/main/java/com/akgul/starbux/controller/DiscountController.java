package com.akgul.starbux.controller;

import com.akgul.starbux.entity.Discount;
import com.akgul.starbux.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class DiscountController {

    @Autowired
    private DiscountRepository repository;

    public Discount getDiscountById(Long discountId) {
        return repository.getByIdAndDeleted(discountId, false);
    }

    public List<Discount> getDiscounts() {
        return repository.findAllByDeleted(false);
    }
}
