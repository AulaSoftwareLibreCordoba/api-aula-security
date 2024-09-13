package com.universe.controller;

import com.universe.persistence.entity.UserEntity;
import com.universe.persistence.repository.UserRepository;
import com.universe.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping()
    public List<UserEntity> getUsers() {
        return userService.findAllUsers();
    }
    @PostMapping("/addUser")

    public ResponseEntity<String> saveUser(@RequestBody UserEntity userEntity) {
        try {
            userService.addUser(userEntity);
            return ResponseEntity.ok("User added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add user: " + e.getMessage());
        }
    }

}
