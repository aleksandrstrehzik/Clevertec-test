package ru.clevertec.test.service.data;

import ru.clevertec.test.repository.entity.product.Product;
import ru.clevertec.test.repository.entity.product.Type;

import java.math.BigDecimal;

public class ProductBuilder implements TestBuilder<Product> {

    private Integer id = 1;
    private boolean IsOnDiscount = false;
    private String marking = "";
    private BigDecimal price = new BigDecimal(5);

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductBuilder id(int id) {
        this.setId(id);
        return this;
    }

    private Type type = Type.MILK;
    private String description = "";

    public static ProductBuilder aProduct() {
        return new ProductBuilder();
    }

    public ProductBuilder price(String price) {
        this.setPrice(new BigDecimal(price));
        return this;
    }

    @Override
    public Product build() {
        return Product.builder()
                .price(price)
                .description(description)
                .marking(marking)
                .type(type)
                .IsOnDiscount(IsOnDiscount)
                .id(id)
                .build();
    }
}
