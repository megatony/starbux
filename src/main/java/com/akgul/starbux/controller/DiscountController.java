package com.akgul.starbux.controller;

import com.akgul.starbux.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class DiscountController {

    @Autowired
    private DiscountRepository repository;
}
