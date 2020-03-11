package com.akgul.starbux.service;

import com.akgul.starbux.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProductService productService;

    public List<Object[]> getTotalAmountOfOrdersPerUser() {
        Query query = entityManager.createNativeQuery("SELECT SUM(AMOUNT), USER_ID FROM ORDERS WHERE DELETED = 0 GROUP BY USER_ID ORDER BY SUM(AMOUNT) DESC;");
        List<Object[]> results = query.getResultList();
        return results;
    }

    public Map<String, List<Object[]>> getMostUsedToppingsForDrinks() {
        Map<String, List<Object[]>> results = new HashMap<>();
        for (Product product : productService.getDrinkProducts()) {
            Query query = entityManager.createNativeQuery("SELECT P1.PRODUCT_NAME, (COUNT(CISP1.SIDE_PRODUCTS_ID) * CI1.QUANTITY) TOTAL_COUNT FROM ORDERS O1 " +
                    "LEFT OUTER JOIN CART C1 ON C1.ID = O1.CART_ID " +
                    "LEFT OUTER JOIN CART_ITEM CI1 ON CI1.CART_ID = C1.ID " +
                    "LEFT OUTER JOIN CART_ITEM_SIDE_PRODUCTS CISP1 ON CISP1.CART_ITEM_ID = CI1.ID " +
                    "LEFT OUTER JOIN PRODUCT P1 ON P1.ID = CISP1.SIDE_PRODUCTS_ID " +
                    "LEFT OUTER JOIN PRODUCT P2 ON P2.ID = CI1.DRINK_PRODUCT_ID " +
                    "WHERE CI1.DRINK_PRODUCT_ID = " + product.getId() + " " +
                    "GROUP BY CI1.DRINK_PRODUCT_ID, CISP1.SIDE_PRODUCTS_ID, CI1.QUANTITY HAVING (COUNT(CISP1.SIDE_PRODUCTS_ID) * CI1.QUANTITY) = " +
                    "(SELECT (COUNT(CISP.SIDE_PRODUCTS_ID) * CI.QUANTITY) TOTAL_COUNT FROM ORDERS O " +
                    "LEFT OUTER JOIN CART C ON C.ID = O.CART_ID " +
                    "LEFT OUTER JOIN CART_ITEM CI ON CI.CART_ID = C.ID " +
                    "LEFT OUTER JOIN CART_ITEM_SIDE_PRODUCTS CISP ON CISP.CART_ITEM_ID = CI.ID " +
                    "LEFT OUTER JOIN PRODUCT P ON P.ID = CISP.SIDE_PRODUCTS_ID " +
                    "WHERE CI.DRINK_PRODUCT_ID = " + product.getId() + " " +
                    "GROUP BY CI.DRINK_PRODUCT_ID, CISP.SIDE_PRODUCTS_ID, CI.QUANTITY ORDER BY (COUNT(CISP.SIDE_PRODUCTS_ID) * CI.QUANTITY) DESC LIMIT 1);");

            List<Object[]> queryResult = query.getResultList();

            if (queryResult.size() > 0) {
                results.put(product.getProductName(),query.getResultList());
            }
        }

        return results;
    }

}
