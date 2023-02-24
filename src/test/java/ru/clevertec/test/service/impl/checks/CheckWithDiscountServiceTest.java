package ru.clevertec.test.service.impl.checks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.test.repository.entity.Card;
import ru.clevertec.test.repository.entity.product.Product;
import ru.clevertec.test.service.data.CardBuilder;
import ru.clevertec.test.service.data.ProductBuilder;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@ExtendWith(MockitoExtension.class)
class CheckWithDiscountServiceTest {

    @Spy
    private SimpleCheckService simpleCheckService;

    @InjectMocks
    private CheckWithDiscountService checkWithDiscountService;

    static Stream<Arguments> getCheckNominationCostArguments() {
        Product bread = ProductBuilder.aProduct().price("10").build();
        Product milk = ProductBuilder.aProduct().price("23").build();
        Product chocolate = ProductBuilder.aProduct().price("70").build();
        return Stream.of(
                arguments(bread, 5, "45.0"),
                arguments(milk, 8, "165.6"),
                arguments(chocolate, 9, "567.0"));
    }

    @Test
    void CheckGetShoppingСardShouldBeEmpty() {
        Mockito.doReturn(new HashMap<>())
                .when(simpleCheckService).getShoppingСart();

        assertThat(checkWithDiscountService.getShoppingСart()).isEmpty();
    }

    @Test
    void CheckGeneralСost() {
        Map<Product, Integer> shoppingСart = checkWithDiscountService.getShoppingСart();
        Product bread = ProductBuilder.aProduct().price("10.50").build();
        Product bun = ProductBuilder.aProduct().price("5.50").build();
        shoppingСart.put(bread, 10);
        shoppingСart.put(bun, 1);

        Mockito.doReturn(new BigDecimal("0"))
                .when(simpleCheckService).nominationCost(bread, 10);

        assertThat(checkWithDiscountService.generalСost()).isEqualTo(new BigDecimal("5.50"));
    }

    @Nested
    class DiscountAmount {

        @ParameterizedTest
        @MethodSource("getCheckDiscountAmountArguments")
        void CheckDiscountAmount(String discount, String mocReturn, String generalCost, String expected) {
            Card card = CardBuilder.aCard().discount(discount).build();

            Mockito.doReturn(new BigDecimal(mocReturn))
                    .when(simpleCheckService).generalСost();

            assertThat(checkWithDiscountService.discountAmount(card,
                    new BigDecimal(generalCost))).isEqualTo(new BigDecimal(expected));
        }

        @ParameterizedTest
        @MethodSource("getCheckCostWithDiscountArguments")
        void CheckCostWithDiscount(String discount, String cost, String expected) {
            Card card = CardBuilder.aCard().discount(discount).build();

            Mockito.doReturn(new BigDecimal(cost))
                    .when(simpleCheckService).generalСost();

            assertThat(checkWithDiscountService.costWithDiscount(card, new BigDecimal(cost)))
                    .isEqualTo(new BigDecimal(expected));

        }

        static Stream<Arguments> getCheckDiscountAmountArguments() {
            return Stream.of(
                    arguments("10", "15", "10", "6.0"),
                    arguments("15", "800", "300", "545"),
                    arguments("5", "650", "156", "501.80"));
        }

        static Stream<Arguments> getCheckCostWithDiscountArguments() {
            return Stream.of(
                    arguments("10", "200", "180"),
                    arguments("40", "800", "480"),
                    arguments("20", "400", "320"));
        }
    }

    @ParameterizedTest
    @MethodSource("getCheckNominationCostArguments")
    void CheckNominationCost(Product product, Integer amount, String expected) {
        assertThat(checkWithDiscountService.nominationCost(product, amount))
                .isEqualTo(new BigDecimal(expected));
    }
}