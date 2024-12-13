package com.ofg.transactions.controller;

import com.ofg.transactions.model.User;
import com.ofg.transactions.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/find")
    public User getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping("/create-rollback")
    public String createUserRollback(@RequestBody User user) {
        try {
            userService.createUserWithRollback(user);
            return "User created successfully.";
        } catch (Exception e) {
            return "User creation failed: " + e.getMessage();
        }
    }

    @PostMapping("/create-timeout")
    public String createUserTimeout(@RequestBody User user) {
        try {
            userService.createUserWithTimeout(user);
            return "User created successfully.";
        } catch (Exception e) {
            return "User creation failed: " + e.getMessage();
        }
    }

    @PostMapping("/create-without-rollback")
    public String createUserWithNoRollback(@RequestBody User user) {
        try {
            userService.createUserWithNoRollback(user);
            return "User created successfully.";
        } catch (IllegalArgumentException e) {
            return "Handled without rollback: " + e.getMessage();
        } catch (Exception e) {
            return "User creation failed and transaction rolled back: " + e.getMessage();
        }
    }
}
