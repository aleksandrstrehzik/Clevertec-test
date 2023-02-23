package ru.clevertec.test.repository.factories;

import org.springframework.stereotype.Component;
import ru.clevertec.test.repository.entity.Card;
import ru.clevertec.test.service.exception.IncorrectInput;

import java.math.BigDecimal;

@Component
public class CardFactory {

    public static final String CARD_1234 = "Card-1234";
    public static final String CARD_1235 = "Card-1235";
    public static final String CARD_1236 = "Card-1236";
    public static final String CARD_1237 = "Card-1237";

    public Card getCard(String card) {
        switch (card) {
            case CARD_1234:
                return new Card(1, BigDecimal.valueOf(10), CARD_1234);
            case CARD_1235:
                return new Card(2, BigDecimal.valueOf(20), CARD_1235);
            case CARD_1236:
                return new Card(3, BigDecimal.valueOf(12), CARD_1236);
            case CARD_1237:
                return new Card(4, BigDecimal.valueOf(19), CARD_1237);
            default:
                return null;
        }
    }
}
