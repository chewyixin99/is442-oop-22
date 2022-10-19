package com.is442.oop.user;

import java.util.*;

import org.springframework.stereotype.Component;

import com.is442.oop.data.models.User;
import com.is442.oop.exception.ResourceNotFoundException;

@Component
public interface UserService {
    User getUser(Integer userId) throws ResourceNotFoundException;
    List<User> getAllUsers();
    User createUser(CreateUserRequest createUserRequest);
    User updateUser(Integer userId, UpdateUserRequest updateUserRequest) throws ResourceNotFoundException;
    User deleteUser(Integer userId) throws ResourceNotFoundException;
}
