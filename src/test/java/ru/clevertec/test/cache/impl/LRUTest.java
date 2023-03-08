package ru.clevertec.test.cache.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.test.cache.CacheAlgorithm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LRUTest {

    private CacheAlgorithm<Integer, String> cache;

    @BeforeEach
    void setUp() {
        cache = new LRU<>(4);
        cache.put(1, "1");
        cache.put(2, "2");
        cache.put(3, "3");
        cache.put(4, "4");
    }

    @Test
    void CheckGetShouldBeEquals() {
        assertThat(cache.get(1)).isEqualTo("1");
    }

    @Test
    void CheckGetShouldBeNull() {
        assertThat(cache.get(34)).isNull();
    }

    @Test
    void CheckPutShouldDeleteLast() {
        cache.put(5, "5");

        assertThat(cache.get(1)).isNull();
    }

    @Test
    void CheckDelete() {
        cache.delete(1);

        assertThat(cache.get(1)).isNull();
    }
}