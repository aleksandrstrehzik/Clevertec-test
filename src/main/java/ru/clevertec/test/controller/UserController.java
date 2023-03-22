package ru.clevertec.test.controller;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.test.dto.UserDto;
import ru.clevertec.test.service.impl.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@JacksonXmlRootElement
public class UserController {

    public static final String APPLICATION_XML = "application/xml";
    /**
     * Service field
     */
    private final UserService userService;

    /**
     * Enters method
     * @params userDto - empty UserDto
     * @return to the opening page
     */
    @GetMapping("/")
    public String mainPage(@ModelAttribute("user") UserDto userDto, Model model) {
        model.addAttribute("user", userDto);
        return "main-page";
    }

    /**
     * Displays all existing users
     * @return All users
     */
    @ResponseBody
    @GetMapping(value = "/getAll", produces = APPLICATION_XML)
    public List<UserDto> getUsers() {
        return userService.getAll();
    }

    /**
     * Delete user
     * @param id delete user id
     */
    @ResponseBody
    @GetMapping("/delete/{id}")
    public void deleteUsers(@PathVariable("id") Integer id) {
        userService.delete(id);
    }

    /**
     * Add user
     * @param userDto user to add
     * @return redirect to main-page
     */
    @PostMapping("/add")
    public String addUsers(@ModelAttribute("user") @Valid UserDto userDto,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/";
        }
        userService.add(userDto);
        return "redirect:/";
    }

    /**
     * Update user
     * @param userDto user to add
     * @return redirect to main-page
     */
    @PostMapping("/update")
    public String updateUsers(@ModelAttribute("user") @Valid UserDto userDto,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/";
        }
        userService.update(userDto);
        return "redirect:/";
    }
    /**
     * Get user by id
     * @param id update user id
     * @return userDto or null
     */
    @ResponseBody
    @GetMapping(value = "/get/{id}", produces = APPLICATION_XML)
    public UserDto getUser(@PathVariable("id") Integer id) {
        return userService.getById(id);
    }
}
