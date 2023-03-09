package ru.clevertec.test.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull
    private String id;
    @NotNull
    @Size(min = 2, max = 20, message = "Длина должна быть в пределах от 2 до 20 символов")
    private String name;
    private String country;
    private Integer age;
    private String city;
    @Pattern(regexp = "(\\+375)? ?\\(\\d{2}\\) ?\\d{3}-\\d{2}-\\d{2}")
    private String phoneNumber;
}
