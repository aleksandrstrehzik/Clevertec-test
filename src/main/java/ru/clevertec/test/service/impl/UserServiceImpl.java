package ru.clevertec.test.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.test.repository.dao.fake.UserDAO;
import ru.clevertec.test.repository.entity.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    /**
     * Database field
     */
    private final UserDAO userDAO;

    /**
     * Get a user from the database
     * @param id - requested user with id
     * @return User or null
     */
    @Override
    public User getById(Integer id) {
        return userDAO.getById(id);
    }

    /**
     * Returning All from the database
     * @return All users from database
     */
    @Override
    public List<User> getAll() {
        return userDAO.getAll();
    }

    /**
     * Add a user to the database
     * @param user - user to add to database
     */
    @Override
    public void add(User user) {
        userDAO.post(user);
    }

    /**
     * delete a user to the database
     * @param id - user to delete id
     */
    @Override
    public void delete(Integer id) {
        userDAO.delete(id);
    }

    /**
     * Update a user from database
     * @param user - user to update in database
     */
    @Override
    public void update(User user) {
        userDAO.put(user);
    }
}
