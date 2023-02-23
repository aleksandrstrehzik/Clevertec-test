package ru.clevertec.test.repository.entity.product.builders;


import ru.clevertec.test.repository.entity.product.ProductBuilder;
import ru.clevertec.test.repository.entity.product.Type;

import java.math.BigDecimal;

public class BreadBuilder extends ProductBuilder {
    @Override
    public void marking() {
        product.setMarking("Flour, water and salt");
    }

    @Override
    public void type() {
        product.setType(Type.BAKE);
    }

    @Override
    public void price() {
        product.setPrice(BigDecimal.valueOf(10.5));
    }

    @Override
    public void discount() {
        product.setIsOnDiscount(true);
    }

    @Override
    public void description() {
        product.setDescription("Bread");
    }

    @Override
    public void id() {
        product.setId(1);
    }
}
