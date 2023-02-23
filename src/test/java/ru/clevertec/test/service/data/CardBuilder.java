package ru.clevertec.test.service.data;

import ru.clevertec.test.repository.entity.Card;

import java.math.BigDecimal;

public class CardBuilder implements TestBuilder<Card> {

    private Integer id = 1;
    private BigDecimal discount = new BigDecimal("5");
    private String name = "Card-1234";

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public static CardBuilder aCard() {
        return new CardBuilder();
    }

    public CardBuilder discount(String d) {
        this.setDiscount(new BigDecimal(d));
        return this;
    }

    @Override
    public Card build() {
        return Card.builder()
                .id(id)
                .discount(discount)
                .name(name)
                .build();
    }
}
