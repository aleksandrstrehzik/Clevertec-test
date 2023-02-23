package ru.clevertec.test.service.impl.checks;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.clevertec.test.repository.entity.Card;
import ru.clevertec.test.repository.entity.product.Product;
import ru.clevertec.test.service.exception.CardNotFoundException;
import ru.clevertec.test.service.interfaces.checks.CheckService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Service
@Qualifier("Simple")
public class SimpleCheckService implements CheckService {

    private final Map<Product, Integer> shoppingСart = new HashMap<>();

    @Override
    public Map<Product, Integer> getShoppingСart() {
        return shoppingСart;
    }

    @Override
    public BigDecimal generalСost() {
        BigDecimal cost = BigDecimal.valueOf(0);
        for (Product product : getShoppingСart().keySet()) {
            cost = cost.add(nominationCost(product, getShoppingСart().get(product)));
        }
        return cost;
    }

    @Override
    public BigDecimal discountAmount(Card card, BigDecimal cost) {
        if (card != null) {
            return cost.divide(new BigDecimal(100)).multiply(card.getDiscount())
                    .setScale(3, RoundingMode.CEILING);
        } else throw new CardNotFoundException();
    }

    @Override
    public BigDecimal costWithDiscount(Card card, BigDecimal cost) {
        if (card != null) {
            return cost.subtract(discountAmount(card, cost));
        } else throw new CardNotFoundException();
    }

    @Override
    public BigDecimal nominationCost(Product product, Integer value) {
        return product.getPrice().multiply(BigDecimal.valueOf(value));
    }
}
