package com.is442.oop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.is442.oop.data.models.User;
import com.is442.oop.data.payloads.request.*;
import com.is442.oop.data.payloads.response.MessageResponse;
import com.is442.oop.service.UserService;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;
    
    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable("userId") Integer userId) {
        User user = null;
        try {
            user = userService.getUser(userId);
        } catch (Exception e) {
            return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<MessageResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        MessageResponse newUser = userService.createUser(createUserRequest);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable("userId") Integer userId, @RequestBody UpdateUserRequest updateUserRequest) {
        User updateUser = null;
        try {
            updateUser = userService.updateUser(userId, updateUserRequest);
        } catch (Exception e) {
            return new ResponseEntity<>(updateUser, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("userId") Integer userId) {
        try {
            userService.deleteUser(userId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
