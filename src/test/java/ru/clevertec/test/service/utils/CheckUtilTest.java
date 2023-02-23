package ru.clevertec.test.service.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class CheckUtilTest {

    public static final String CARD_1234 = "Card-1234";
    private static final String[] orderWithCard = {"1-3", "3-4", "4-7", CARD_1234};
    private static final String[] orderWithoutCard = {"1-3", "2-2", "4-9"};
    private static final int[] itemIdOrderWithCard = {1, 3, 4};
    private static final int[] itemIdOrderWithoutCard = {1, 2, 4};
    private static final int[] itemAmountOrderWithCard = {3, 4, 7};
    private static final int[] itemAmountWithoutCard = {3, 2, 9};

    static Stream<Arguments> getIdArrayArguments() {
        return Stream.of(
                arguments(orderWithCard, itemIdOrderWithCard),
                arguments(orderWithoutCard, itemIdOrderWithoutCard)
        );
    }

    static Stream<Arguments> getNumArrayArguments() {
        return Stream.of(
                arguments(orderWithCard, itemAmountOrderWithCard),
                arguments(orderWithoutCard, itemAmountWithoutCard)
        );
    }

    static Stream<Arguments> isCardPresentedArguments() {
        return Stream.of(
                arguments(orderWithCard, true),
                arguments(orderWithoutCard, false)
        );
    }

    @ParameterizedTest
    @MethodSource("getIdArrayArguments")
    void checkGetIdArrayShouldBeEqual(String[] order, int[] id) {
        assertThat(CheckUtil.getIdArray(order)).isEqualTo(id);
    }

    @ParameterizedTest
    @MethodSource("getNumArrayArguments")
    void getNumArray(String[] order, int[] id) {
        assertThat(CheckUtil.getNumArray(order)).isEqualTo(id);
    }

    @Test
    void checkGetDiscountCard() {
        assertThat(CheckUtil.getDiscountCard(orderWithCard)).isEqualTo(CARD_1234);
    }

    @ParameterizedTest
    @MethodSource("isCardPresentedArguments")
    void CheckIsCardPresent(String[] order, boolean IsPresented) {
        assertThat(CheckUtil.IsCardPresent(order)).isEqualTo(IsPresented);
    }
}