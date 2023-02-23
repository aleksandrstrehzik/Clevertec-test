package ru.clevertec.test.service.exception;

public class NullInput extends IllegalArgumentException {

    public NullInput() {
        super("You enter nothing");
    }
}
