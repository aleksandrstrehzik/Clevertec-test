package ru.clevertec.test.service.impl.checks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.clevertec.test.repository.entity.Card;
import ru.clevertec.test.repository.entity.product.Product;
import ru.clevertec.test.repository.factories.CardFactory;
import ru.clevertec.test.repository.factories.ProductFactory;
import ru.clevertec.test.service.data.ProductBuilder;
import ru.clevertec.test.service.exception.CardNotFoundException;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SimpleCheckServiceTest {

    private SimpleCheckService simpleCheckService;

    @BeforeEach
    void setUp() {
        simpleCheckService = new SimpleCheckService();
    }

    @Test
    void getShoppingСart() {
        assertThat(simpleCheckService.getShoppingСart()).isEmpty();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/simpleCheckTestData/general-cost-test-data.csv", delimiter = ',', numLinesToSkip = 1)
    void checkGeneralCostShouldBeEqual(int breadAmount, int bunAmount, int milkAmount,
                                       int yogurtAmount, String generalCost) {
        Map<Product, Integer> shoppingСart = simpleCheckService.getShoppingСart();
        Product bread = ProductBuilder.aProduct().price("10.50").build();
        Product bun = ProductBuilder.aProduct().price("5.50").build();
        Product milk = ProductBuilder.aProduct().price("34.22").build();
        Product yogurt = ProductBuilder.aProduct().price("56.26").build();
        shoppingСart.put(bread, breadAmount);
        shoppingСart.put(bun, bunAmount);
        shoppingСart.put(milk, milkAmount);
        shoppingСart.put(yogurt, yogurtAmount);

        assertThat(simpleCheckService.generalСost()).isEqualTo(new BigDecimal(generalCost));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/simpleCheckTestData/discount-amount-data.csv", delimiter = ',', numLinesToSkip = 1)
    void checkDiscountAmountShouldBeEqual(String cost, String expected, String cardNumber) {
        Card card = new CardFactory().getCard(cardNumber);
        BigDecimal bigDecimal = simpleCheckService.discountAmount(card, new BigDecimal(cost));

        assertThat(bigDecimal).isEqualTo(new BigDecimal(expected));
    }

    @ParameterizedTest
    @ValueSource(strings = {"wed", "sdd", "Card-3212", ""})
    void checkDiscountAmountShouldThrowCardNotFoundException(String cardNumber) {
        BigDecimal bigDecimal = new BigDecimal("783.78");
        Card card = new CardFactory().getCard(cardNumber);

        assertThrows(CardNotFoundException.class,
                () -> simpleCheckService.discountAmount(card, bigDecimal));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/simpleCheckTestData/cost-with-discount-data.csv", delimiter = ',', numLinesToSkip = 1)
    void checkCostWithDiscountShouldBeEquals(String cost, String expected, String cardNumber) {
        Card card = new CardFactory().getCard(cardNumber);
        BigDecimal cost1 = new BigDecimal(cost);
        BigDecimal expected1 = new BigDecimal(expected);

        assertThat(simpleCheckService.costWithDiscount(card, cost1)).isEqualTo(expected1);
    }

    @ParameterizedTest
    @ValueSource(strings = {"wed", "sdd", "Card-3212", ""})
    void checkCostWithDiscountShouldThrowCardNotFoundException(String cardNumber) {
        BigDecimal bigDecimal = new BigDecimal("783.78");
        Card card = new CardFactory().getCard(cardNumber);

        assertThrows(CardNotFoundException.class,
                () -> simpleCheckService.discountAmount(card, bigDecimal));
    }

    @Test
    void nominationCost() {
        int value = 5;
        ProductFactory productFactory = new ProductFactory();
        Product bread = productFactory.getProduct(1);
        BigDecimal expected = bread.getPrice().multiply(new BigDecimal(value));

        assertThat(simpleCheckService.nominationCost(bread, value)).isEqualTo(expected);
    }
}