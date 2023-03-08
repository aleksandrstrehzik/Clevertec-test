package ru.clevertec.test.repository.dao.fake;

import lombok.ToString;
import org.springframework.stereotype.Repository;
import ru.clevertec.test.annotation.Cache;
import ru.clevertec.test.repository.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Strezhik
 */
@Repository
@ToString
public class UserDaoImpl implements UserDAO {

    /**
     * Database replacement field
     */
    private final Map<Integer, User> database;

    public UserDaoImpl() {
        this.database = new HashMap<>();
        init();
    }

    /**
     * Method introducing initial values
     */
    private void init() {
        database.put(1, User.builder().id(1).name("Serhio").country("Italy").city("Milan").age(20).phoneNumber("+375 (25) 787-75-62").build());
        database.put(2, User.builder().id(2).name("Sancho").country("Argentina").city("Salta").age(24).phoneNumber("+375 (25) 787-75-62").build());
        database.put(3, User.builder().id(3).name("Mihail").country("Russia").city("Moscow").age(8).phoneNumber("+375 (25) 787-75-53").build());
        database.put(4, User.builder().id(4).name("Mario").country("Mexico").city("Mexico").age(40).phoneNumber("+375 (25) 787-75-60").build());
        database.put(5, User.builder().id(5).name("Sveta").country("Belarus").city("Postavi").age(19).phoneNumber("+375 (25) 902-75-62").build());
    }

    /**
     * Get a user from the database
     * @param id - requested user with id
     * @return User or null
     */
    @Override
    @Cache
    public User getById(Integer id) {
        return database.get(id);
    }

    /**
     * Returning All from the database
     * @return All users from database
     */
    @Override
    public List<User> getAll() {
        return new ArrayList<>(database.values());
    }

    /**
     * Add a user to the database
     * @param user - user to add to database
     */
    @Override
    @Cache
    public void post(User user) {
        database.putIfAbsent(user.getId(), user);
    }

    /**
     * delete a user to the database
     * @param id - user to delete id
     */
    @Override
    @Cache
    public void delete(Integer id) {
        database.remove(id);
    }

    /**
     * Update a user from database
     * @param user - user to update in database
     */
    @Override
    @Cache
    public void put(User user) {
        if (database.containsKey(user.getId())) {
            database.put(user.getId(), user);
    }
}

}
