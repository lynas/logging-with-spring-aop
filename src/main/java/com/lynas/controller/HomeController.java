package com.lynas.controller;

import com.lynas.LogInputOutput;
import com.lynas.domain.User;
import org.springframework.web.bind.annotation.*;

@RestController
class HomeController {

    @LogInputOutput
    @GetMapping("/users/{userId}")
    public User getUserById(@PathVariable Long userId){
        return new User(userId, "userName " + userId, "password" + userId);
    }

    @LogInputOutput
    @PostMapping("/users/create")
    public User createUser(@RequestBody User user) {
        return user;
    }
}
