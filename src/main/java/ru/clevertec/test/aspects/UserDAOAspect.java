package ru.clevertec.test.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.clevertec.test.cache.CacheAlgorithm;
import ru.clevertec.test.repository.entity.User;
import ru.clevertec.test.repository.factories.CacheAlgorithmFactory;

/**
 * @Author Strezhik
 */

@Aspect
@Component
public class UserDAOAspect {

    /**
     * Field being a DAO cache
     */
    private final CacheAlgorithm<Integer, User> cache;

    public UserDAOAspect(CacheAlgorithmFactory factory) {
        this.cache = (CacheAlgorithm<Integer, User>) factory.getAlgorithm();
    }

    /**
     * Predicate to determine if a method is annotated with {@link @Cache}
     */
    @Pointcut("@annotation(ru.clevertec.test.annotation.Cache)")
    public void isCacheAnnotationPresent() {
    }

    /**
     * advice returning a value from the cache or if it is not there from the dao
     * @param id - requested user with id
     * @return User or null
     */
    @Around("isCacheAnnotationPresent() && execution(public * getById(Integer)) && args(id)")
    public Object userDAOGetById(ProceedingJoinPoint point, Integer id) throws Throwable {
        User user = cache.get(id);
        if (user != null) return user;
        else {
            User proceed = (User) point.proceed();
            cache.put(proceed.getId(), proceed);
            return proceed;
        }
    }

    /**
     * advice adds value to cache and to dao
     * @param user - user to add
     */
    @Around("isCacheAnnotationPresent() && execution(public * add(..)) && args(user)")
    public void userDAOAdd(ProceedingJoinPoint point, User user) throws Throwable {
        cache.put(user.getId(), user);
        point.proceed();
    }

    /**
     * advice delete user from dao and cache
     * @param id - user id
     */
    @Around("isCacheAnnotationPresent() && execution(public * delete(Integer)) && args(id)")
    public void userDAODelete(ProceedingJoinPoint point, Integer id) throws Throwable {
        point.proceed();
        cache.delete(id);
    }

    /**
     * advice update user
     * @param user - user to update
     */
    @Around("isCacheAnnotationPresent() && execution(public * put(..)) && args(user)")
    public void userDAOPut(ProceedingJoinPoint point, User user) throws Throwable {
        point.proceed();
        cache.put(user.getId(), user);
    }
}
