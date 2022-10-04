package com.is442.oop.daos;

import java.util.*;

import org.springframework.stereotype.Component;

import com.is442.oop.data.models.User;
import com.is442.oop.data.payloads.request.CreateUserRequest;
import com.is442.oop.data.payloads.request.UpdateUserRequest;
import com.is442.oop.data.payloads.response.MessageResponse;
import com.is442.oop.exceptions.ResourceNotFoundException;

@Component
public interface UserDAOInt {
    User getUser(Integer userId) throws ResourceNotFoundException;
    List<User> getAllUsers();
    MessageResponse createUser(CreateUserRequest createUserRequest);
    User updateUser(Integer userId, UpdateUserRequest updateUserRequest) throws ResourceNotFoundException;
    void deleteUser(Integer userId) throws ResourceNotFoundException;
}
