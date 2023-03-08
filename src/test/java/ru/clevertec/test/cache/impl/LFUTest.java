package ru.clevertec.test.cache.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.test.cache.CacheAlgorithm;

import static org.assertj.core.api.Assertions.assertThat;

class LFUTest {

    private CacheAlgorithm<Integer, String> cache;

    @BeforeEach
    void setUp() {
        cache = new LFU<>(4);
        cache.put(1, "1");
        cache.put(2, "2");
        cache.put(3, "3");
        cache.put(4, "4");
    }

    @Test
    void CheckGetShouldIncrementFrequency() {
        cache.get(1);
        cache.get(1);

        cache.put(5, "5");
        cache.put(6, "6");
        cache.put(7, "7");
        cache.put(8, "8");

        assertThat(cache.get(1)).isEqualTo("1");
    }

    @Test
    void CheckGetShouldReturnNull() {
        assertThat(cache.get(348)).isEqualTo(null);
    }

    @Test
    void CheckPutShouldBeFIFO() {
        cache.put(5, "5");

        assertThat(cache.get(1)).isNull();
    }

    @Test
    void CheckDelete() {
        cache.delete(1);

        assertThat(cache.get(1)).isNull();
    }
}