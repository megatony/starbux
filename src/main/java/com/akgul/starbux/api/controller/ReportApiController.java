package com.akgul.starbux.api.controller;

import com.akgul.starbux.api.controller.response.*;
import com.akgul.starbux.entity.User;
import com.akgul.starbux.enums.UserType;
import com.akgul.starbux.service.ReportService;
import com.akgul.starbux.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report")
@PreAuthorize("hasRole('ADMIN')")
public class ReportApiController {
    private Logger LOGGER = LoggerFactory.getLogger(ReportApiController.class);

    @Autowired
    private ReportService reportService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/orderamountperuser", method = RequestMethod.GET)
    public List<StarbuxApiResponse> getOrderAmountPerUser(Authentication authentication) {
        List<StarbuxApiResponse> responses = new ArrayList<>();

        User user = userService.getUserByUserName(authentication.getName());

        if (ObjectUtils.isEmpty(user) || !user.getUserType().equals(UserType.ADMIN)) {
            LOGGER.error("Illegal admin API access.");
            responses.add(new StarbuxMethodNotAllowedApiResponse("User not exist or not have access to this method."));
            return responses;
        }

        for (Object[] item : reportService.getTotalAmountOfOrdersPerUser()) {
            responses.add(new TotalAmountOfOrdersApiResponse(item));
        }

        if (CollectionUtils.isEmpty(responses)) {
            responses.add(new StarbuxNotFoundApiResponse("Order amount per user report is empty."));
            return responses;
        }

        return responses;
    }


    @RequestMapping(value = "/mostusedsideproducts", method = RequestMethod.GET)
    public List<StarbuxApiResponse> getMostUsedToppings(Authentication authentication) {
        List<StarbuxApiResponse> responses = new ArrayList<>();

        User user = userService.getUserByUserName(authentication.getName());

        if (ObjectUtils.isEmpty(user) || !user.getUserType().equals(UserType.ADMIN)) {
            LOGGER.error("Illegal admin API access.");
            responses.add(new StarbuxMethodNotAllowedApiResponse("User not exist or not have access to this method."));
            return responses;
        }

        for (Map.Entry<String, List<Object[]>> item : reportService.getMostUsedToppingsForDrinks().entrySet()) {
            responses.add(new MostUsedToppingApiResponse(item));
        }

        if (CollectionUtils.isEmpty(responses)) {
            responses.add(new StarbuxNotFoundApiResponse("Most used side products report is empty."));
            return responses;
        }

        return responses;
    }
}
