package ru.clevertec.test.repository.entity;

import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;
    private String name;
    private String country;
    private Integer age;
    private String city;
    @Pattern(regexp = "(\\+375)? ?\\(\\d{2}\\) ?\\d{3}-\\d{2}-\\d{2}")
    private String phoneNumber;
}
