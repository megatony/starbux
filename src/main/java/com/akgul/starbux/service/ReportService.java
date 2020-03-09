package com.akgul.starbux.service;

import com.akgul.starbux.entity.CartItem;
import com.akgul.starbux.entity.Order;
import com.akgul.starbux.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service
public class ReportService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private OrderService orderService;

    public List<Object[]> getTotalAmountOfOrdersPerUser() {
        Query query = entityManager.createNativeQuery("SELECT SUM(AMOUNT), USER_ID FROM ORDERS WHERE DELETED = 0 GROUP BY USER_ID ORDER BY SUM(AMOUNT) DESC;");
        List<Object[]> results = query.getResultList();
        return results;
    }

    public List<Object[]> getMostUsedToppingsForDrinks() {
        Query query = entityManager.createNativeQuery("SELECT SUM(AMOUNT), USER_ID FROM ORDERS WHERE DELETED = 0 GROUP BY USER_ID ORDER BY SUM(AMOUNT) DESC;");
        List<Object[]> results = query.getResultList();
        return results;
    }

}
