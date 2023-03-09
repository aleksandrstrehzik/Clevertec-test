package ru.clevertec.test.repository.factories;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import ru.clevertec.test.cache.CacheAlgorithm;
/**
 * @Author Strezhik
 */
@Component
public class CacheAlgorithmFactory {

    public CacheAlgorithm<?,?> getAlgorithm() {
        return getCacheAlgorithmBean();
    }

    /**
     * Gives bean of the declared type, if it exists
     * @return CacheAlgorithm<?,?> or null
     */
    @Lookup
    CacheAlgorithm<?,?> getCacheAlgorithmBean() {
        return null;
    }
}
