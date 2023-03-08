package ru.clevertec.test.service.impl;

import ru.clevertec.test.repository.entity.User;

import java.util.List;

public interface UserService {
    User getById(Integer id);
    List<User> getAll();
    void add(User t);
    void delete(Integer id);
    void update(User t);
}
