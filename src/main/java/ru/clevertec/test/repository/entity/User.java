package ru.clevertec.test.repository.entity;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;
    private String name;
    private String country;
    private Integer age;
    private String city;
    private String phoneNumber;
}
