package com.example.ProjectJavaSpring.controller;

import com.example.ProjectJavaSpring.models.User;
import com.example.ProjectJavaSpring.models.ResponseObject;
import com.example.ProjectJavaSpring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/Users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> createUser(@RequestBody User newUser) {
        return userService.createUser(newUser);
    }
}