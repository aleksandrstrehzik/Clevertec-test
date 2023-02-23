package ru.clevertec.test.repository.entity.product;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Product {

    @Id
    private Integer id;
    private boolean IsOnDiscount;
    private String marking;
    private BigDecimal price;
    private Type type;
    private String description;
}