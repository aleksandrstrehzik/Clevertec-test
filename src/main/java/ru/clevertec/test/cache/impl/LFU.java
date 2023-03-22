package ru.clevertec.test.cache.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ru.clevertec.test.cache.CacheAlgorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Strezhik
 */
@ConditionalOnProperty(prefix = "cache", name = "algorithm", havingValue = "LFU")
@Component
public class LFU<ID, T> implements CacheAlgorithm<ID, T> {

    /**
     * Field containing the cache
     */
    private final Map<ID, T> cache;

    /**
     * Field containing the frequency of using elements from the cache
     */
    private final Map<ID, Integer> frequencyMap;

    /**
     * Cache size
     */
    private final int cacheSize;

    public LFU(@Value("${cache.size}") int cacheSize) {
        this.cacheSize = cacheSize;
        this.cache = new HashMap<>();
        this.frequencyMap = new HashMap<>();
    }

    /**
     * Method returning a value from the cache
     * @param id - requested user with id
     * @return User or null
     */
    @Override
    public T get(ID id) {
        if (cache.containsKey(id)) {
            frequencyMap.put(id, getAndIncrement(id));
            return cache.get(id);
        }
        return null;
    }

    /**
     * Adds an element to the cache. If the cache is full removes the most rarely used value
     * @param id - object id
     * @param object - object to add
     */
    @Override
    public void put(ID id, T object) {
        if (!cache.containsKey(id) && cache.size() < cacheSize) {
            frequencyMap.put(id, 1);
            cache.put(id, object);
        }
        if (!cache.containsKey(id) && cache.size() == cacheSize) {
            ID mostRarelyUsed = getMostRarelyUsed();
            frequencyMap.remove(mostRarelyUsed);
            cache.remove(mostRarelyUsed);
            frequencyMap.put(id, 1);
            cache.put(id, object);
        } else cache.put(id, object);
    }

    /**
     * Delete an element to the cache
     * @param id - object id
     */
    @Override
    public void delete(ID id) {
        frequencyMap.remove(id);
        cache.remove(id);

    }

    /**
     * Increments the element's frequency counter
     * @param id - object id
     * @return frequency counter
     */
    private Integer getAndIncrement(ID id) {
        return frequencyMap.get(id) + 1;
    }

    /**
     * Find the least used item in the cache
     * @return id - object id or null if cache empty
     */
    private ID getMostRarelyUsed() {
        return frequencyMap.entrySet().stream()
                .filter(set -> set.getValue().equals(frequencyMap.values().stream()
                        .min(Integer::compareTo)
                        .orElse(null)))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);

    }
}
