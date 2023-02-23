package ru.clevertec.test.service.interfaces.checks;

import ru.clevertec.test.repository.entity.Card;
import ru.clevertec.test.repository.entity.product.Product;

import java.math.BigDecimal;
import java.util.Map;

public interface CheckService {
    Map<Product, Integer> getShoppingСart();

    BigDecimal generalСost();

    BigDecimal discountAmount(Card card, BigDecimal cost);

    BigDecimal costWithDiscount(Card card, BigDecimal cost);

    BigDecimal nominationCost(Product product, Integer value);
}
