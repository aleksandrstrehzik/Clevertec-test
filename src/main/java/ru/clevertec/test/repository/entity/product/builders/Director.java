package ru.clevertec.test.repository.entity.product.builders;

import ru.clevertec.test.repository.entity.product.Product;
import ru.clevertec.test.repository.entity.product.ProductBuilder;

public class Director {
    private ProductBuilder productBuilder;

    public void setProductBuilder(ProductBuilder p) {
        productBuilder = p;
    }

    public Product build() {
        productBuilder.createProduct();
        productBuilder.marking();
        productBuilder.type();
        productBuilder.price();
        productBuilder.discount();
        productBuilder.description();
        productBuilder.id();
        return productBuilder.getProduct();
    }
}
