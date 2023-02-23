package ru.clevertec.test.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "discount_card")
public class Card {
    @Id
    private Integer id;
    private BigDecimal discount;
    private String name;

}
