package ru.clevertec.test.cache.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ru.clevertec.test.cache.CacheAlgorithm;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author Strezhik
 */
@ConditionalOnProperty(prefix = "cache", name = "algorithm", havingValue = "LRU")
@Component
public class LRU<ID, T> implements CacheAlgorithm<ID, T> {

    /**
     * Field containing the cache
     */
    private final Map<ID, T> cache;

    public LRU(@Value("${cache.size}") int cacheSize) {
        this.cache = new LinkedHashMap<>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<ID, T> eldest) {
                return cacheSize < cache.size();
            }
        };
    }

    /**
     * Advice returning a value from the cache
     * @param id - requested user with id
     * @return User or null
     */
    @Override
    public T get(ID id) {
        if (cache.containsKey(id)) {
            T removedObject = cache.remove(id);
            cache.put(id, removedObject);
            return removedObject;
        }
        return null;
    }

    /**
     * Adds an element to the cache. If the cache is full removes the value used earlier than the rest
     * @param id - object id
     * @param object - object to add
     */
    @Override
    public void put(ID id, T object) {
        cache.put(id, object);
    }

    /**
     * Delete an element to the cache
     * @param id - object id
     */
    @Override
    public void delete(ID id) {
        cache.remove(id);
    }
}
