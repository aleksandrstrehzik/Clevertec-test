package ru.clevertec.test.service.exception;

public class CardNotFoundException extends NullPointerException {

    public CardNotFoundException() {
        super("You entered a non-existent card");
    }
}
