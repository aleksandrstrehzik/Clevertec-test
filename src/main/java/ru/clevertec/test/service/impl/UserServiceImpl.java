package ru.clevertec.test.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.test.dto.UserDto;
import ru.clevertec.test.dto.mapper.UserMapper;
import ru.clevertec.test.repository.dao.fake.UserDAO;
import ru.clevertec.test.repository.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    /**
     * Bean to convert user in Dto and conversely
     */
    private final UserMapper userMapper;

    /**
     * Database field
     */
    private final UserDAO userDAO;

    /**
     * Get a user from the database
     * @param id - requested user with id
     * @return UserDto or null
     */
    @Override
    public UserDto getById(Integer id) {
        return userMapper.toDto(userDAO.getById(id));
    }

    /**
     * Returning All from the database
     * @return All users from database
     */
    @Override
    public List<UserDto> getAll() {
        return userDAO.getAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Add a user to the database
     * @param user - user to add to database
     */
    @Override
    public void add(UserDto user) {
        userDAO.post(userMapper.toEntity(user));
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
    public void update(UserDto user) {
        userDAO.put(userMapper.toEntity(user));
    }
}
