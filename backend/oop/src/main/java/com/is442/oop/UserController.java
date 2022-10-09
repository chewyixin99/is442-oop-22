package com.is442.oop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.is442.oop.daos.UserDAOInt;
import com.is442.oop.data.models.User;
import com.is442.oop.data.payloads.request.*;
import com.is442.oop.data.payloads.response.MessageResponse;


import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserDAOInt userDAOInt;
    
    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userDAOInt.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable("userId") Integer userId) {
        User user = null;
        try {
            user = userDAOInt.getUser(userId);
        } catch (Exception e) {
            return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<MessageResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        MessageResponse newUser = userDAOInt.createUser(createUserRequest);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable("userId") Integer userId, @RequestBody UpdateUserRequest updateUserRequest) {
        User updateUser = null;
        try {
            updateUser = userDAOInt.updateUser(userId, updateUserRequest);
        } catch (Exception e) {
            return new ResponseEntity<>(updateUser, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("userId") Integer userId) {
        try {
            userDAOInt.deleteUser(userId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
