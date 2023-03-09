package ru.clevertec.test.cache;

public interface CacheAlgorithm<ID, T> {
    T get(ID id);
    void put(ID id, T object);
    void delete(ID id);
}
