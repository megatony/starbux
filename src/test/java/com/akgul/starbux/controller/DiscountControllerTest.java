package com.akgul.starbux.controller;

import com.akgul.starbux.entity.Discount;
import com.akgul.starbux.enums.DiscountType;
import com.akgul.starbux.repository.DiscountRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DiscountControllerTest {

    @InjectMocks
    private DiscountController discountController;

    @Mock
    private DiscountRepository repository;

    @Test
    public void shouldVerifySaveDiscount() {
        Discount discount = new Discount();
        discount.setId(1L);
        discount.setDiscountAmount(BigDecimal.ONE);
        discount.setDiscountType(DiscountType.PercentageBased);

        when(repository.save(discount)).thenReturn(discount);
        Assert.assertEquals(discountController.saveDiscount(discount), discount);
    }
}
