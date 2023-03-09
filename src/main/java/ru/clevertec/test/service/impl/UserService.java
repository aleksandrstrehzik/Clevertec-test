package ru.clevertec.test.service.impl;

import ru.clevertec.test.dto.UserDto;
import ru.clevertec.test.repository.entity.User;

import java.util.List;

public interface UserService {
    UserDto getById(Integer id);
    List<UserDto> getAll();
    void add(UserDto t);
    void delete(Integer id);
    void update(UserDto t);
}
