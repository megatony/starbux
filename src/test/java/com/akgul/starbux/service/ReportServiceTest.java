package com.akgul.starbux.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReportServiceTest {

    @Mock
    private ReportService reportService;

    @Test
    public void shouldGetTotalAmountOfOrdersPerUser() {
        List<Object[]> list = new ArrayList<>();

        Object[] user1Response = new Object[2];
        user1Response[0] = 123.45;
        user1Response[1] = 1;

        Object[] user2Response = new Object[2];
        user2Response[0] = 1923.00;
        user2Response[1] = 2;

        list.add(user1Response);
        list.add(user2Response);

        when(reportService.getTotalAmountOfOrdersPerUser()).thenReturn(list);

        Assert.assertEquals(reportService.getTotalAmountOfOrdersPerUser().size(), 2);
    }

    @Test
    public void shouldGetMostUsedToppingsForDrinks() {
        Map<String, List<Object[]>> results = new HashMap<>();

        List<Object[]> list = new ArrayList<>();
        Object[] sideProduct1Response = new Object[2];
        sideProduct1Response[0] = "Milk";
        sideProduct1Response[1] = 123;
        list.add(sideProduct1Response);

        List<Object[]> list2 = new ArrayList<>();
        Object[] sideProduct2Response = new Object[2];
        sideProduct2Response[0] = "Lemon";
        sideProduct2Response[1] = 22;
        list2.add(sideProduct2Response);

        results.put("Mocha", list);
        results.put("Tea", list2);

        when(reportService.getMostUsedToppingsForDrinks()).thenReturn(results);
        Assert.assertEquals(reportService.getMostUsedToppingsForDrinks().size(), 2);

    }
}
