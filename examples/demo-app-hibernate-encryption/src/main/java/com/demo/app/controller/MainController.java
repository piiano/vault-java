package com.demo.app.controller;

import com.demo.app.dal.User;
import com.demo.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path="/demo")
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path="/add-user")
    public @ResponseBody String addUser(User user) {

        userService.addUser(user);
        return "Saved";
    }

    @GetMapping(path="/update-user")
    public @ResponseBody String updateUser(User user) {

        if (userService.updateUser(user)) {
            return "Updated";
        }
        return "Not Found";
    }

    @GetMapping(path="/delete-user")
    public @ResponseBody String deleteUser(@RequestParam Integer id) {
        if (userService.deleteUser(id)) {
            return "Deleted";
        }
        return "Not Found";
    }

    @GetMapping(path="/find-user-by-name")
    public @ResponseBody List<User> findUserByName(@RequestParam String name) {

        return userService.findUserByName(name);
    }

    @GetMapping(path="/find-user-by-phone-number")
    public @ResponseBody List<User> findUserByPhoneNumber(@RequestParam(name = "phone_number") String phoneNumber) {

        return userService.findUserByPhoneNumber(phoneNumber);
    }
}
