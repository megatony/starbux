package com.akgul.starbux.api.controller;

import com.akgul.starbux.api.controller.response.*;
import com.akgul.starbux.entity.Order;
import com.akgul.starbux.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/report")
@PreAuthorize("hasRole('ADMIN')")
public class ReportApiController {
    @Autowired
    private ReportService reportService;



    @RequestMapping(value = "/orderamountperuser", method = RequestMethod.GET)
    public List<TotalAmountOfOrdersApiResponse> getOrderAmountPerUser() {
        List<TotalAmountOfOrdersApiResponse> responses = new ArrayList<>();
        for (Object[] item : reportService.getTotalAmountOfOrdersPerUser()) {
            responses.add(new TotalAmountOfOrdersApiResponse(item));
        }
        return responses;
    }


    @RequestMapping(value = "/mostusedsideproducts", method = RequestMethod.GET)
    public List<MostUsedToppingApiResponse> getMostUsedToppings() {
        List<MostUsedToppingApiResponse> responses = new ArrayList<>();
        for (Object[] item : reportService.getMostUsedToppingsForDrinks()) {
            responses.add(new MostUsedToppingApiResponse(item));
            Order order = new Order();
        }
        return responses;
    }
}
