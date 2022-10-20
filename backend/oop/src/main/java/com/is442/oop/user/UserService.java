package com.is442.oop.user;

import java.util.*;

import org.springframework.stereotype.Component;

import com.is442.oop.data.models.User;
import com.is442.oop.data.models.VerificationToken;
import com.is442.oop.exception.ResourceNotFoundException;

@Component
public interface UserService {
    User getUser(Integer userId) throws ResourceNotFoundException;
    List<User> getAllUsers();
    User createUser(UserRequest userRequest);
    User updateUser(Integer userId, UserRequest userRequest) throws ResourceNotFoundException;
    User deleteUser(Integer userId) throws ResourceNotFoundException;

    // All verification methods
    User registerUser(UserRequest userRequest);
    void saveVerificationTokenForUser(String token, User user);
    String validateVerificationToken(String token);
    VerificationToken generateNewVerificationToken(String oldToken);
    User findUserByEmail(String email);
    void createPasswordResetTokenForUser(User user, String token);
    String validatePasswordResetToken(String token);
    Optional<User> getUserByPasswordResetToken(String token);
    void changePassword(User user, String newPassword);
    boolean checkIfValidOldPassword(User user, String oldPassword);
}
