package ru.clevertec.test.controller;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.test.repository.entity.User;
import ru.clevertec.test.service.impl.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@JacksonXmlRootElement
public class UserController {

    /**
     * Service field
     */
    private final UserService userService;

    /**
     * Displays all existing users
     * @return All users
     */
    @GetMapping(value = "/getAll", produces = "application/xml")
    public List<User> getUsers() {
        return userService.getAll();
    }

    @GetMapping("/delete/{id}")
    public void deleteUsers(@PathVariable("id") Integer id) {
        userService.delete(id);
    }

    /**
     * Delete user
     * @param id delete user id
     */
    @GetMapping("/add/{id}")
    public void addUsers(@PathVariable("id") Integer id) {
        userService.add(User.builder().id(id).build());
    }

    /**
     * Update user
     * @param id update user id
     */
    @GetMapping("/update/{id}")
    public void updateUsers(@PathVariable("id") Integer id) {
        userService.update(User.builder().id(id).build());
    }
    /**
     * Get user by id
     * @param id update user id
     * @return user or null
     */
    @GetMapping(value = "/get/{id}", produces = "application/xml")
    public User getUser(@PathVariable("id") Integer id) {
        return userService.getById(id);
    }
}
