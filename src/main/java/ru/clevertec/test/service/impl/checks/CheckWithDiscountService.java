package ru.clevertec.test.service.impl.checks;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.clevertec.test.repository.entity.Card;
import ru.clevertec.test.repository.entity.product.Product;
import ru.clevertec.test.service.interfaces.checks.CheckService;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class CheckWithDiscountService implements CheckService {

    private CheckService checkService;

    public CheckWithDiscountService(@Qualifier("Simple") CheckService checkService) {
        this.checkService = checkService;
    }

    public CheckWithDiscountService() {
    }

    @Override
    public Map<Product, Integer> getShoppingСart() {
        return checkService.getShoppingСart();
    }

    @Override
    public BigDecimal generalСost() {
        BigDecimal cost = BigDecimal.valueOf(0);
        for (Product product : checkService.getShoppingСart().keySet()) {
            Integer value = checkService.getShoppingСart().get(product);
            if (value >= 5 && product.isIsOnDiscount()) {
                cost = cost.add(nominationCost(product, value));
            } else {
                cost = cost.add(checkService.nominationCost(product, value));
            }
        }
        return cost;
    }

    @Override
    public BigDecimal discountAmount(Card card, BigDecimal cost) {
        return (checkService.generalСost().subtract(cost)).add(cost.divide(new BigDecimal("100")).multiply(card.getDiscount()));
    }

    @Override
    public BigDecimal costWithDiscount(Card card, BigDecimal cost) {
        return cost.subtract(discountAmount(card, cost));
    }


    @Override
    public BigDecimal nominationCost(Product product, Integer value) {
        return product.getPrice().multiply(BigDecimal.valueOf(value)).multiply(BigDecimal.valueOf(0.9));
    }
}
