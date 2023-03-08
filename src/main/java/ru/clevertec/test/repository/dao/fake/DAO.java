package ru.clevertec.test.repository.dao.fake;

import java.util.List;

public interface DAO<T> {

    T getById(Integer id);
    List<T> getAll();
    void post(T t);
    void delete(Integer id);
    void put(T t);
}
