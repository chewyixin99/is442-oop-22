package com.is442.oop.user;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.is442.oop.data.models.User;
import com.is442.oop.data.models.VerificationToken;
import com.is442.oop.exception.ResourceNotFoundException;
import com.is442.oop.password.PasswordResetTokenRepository;
import com.is442.oop.verification.VerificationTokenRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getUser(Integer userId) throws ResourceNotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "User ID", userId));
    };

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    };

    @Override
    public User createUser(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        user.setContactNumber(userRequest.getContactNumber());
        user.setUserType(userRequest.getUserType());
        userRepository.save(user);
        return user;
    };

    @Override
    public User updateUser(Integer userId, UserRequest userRequest) throws ResourceNotFoundException {
        User user = null;
        Optional<User> queryUser = userRepository.findById(userId);
        if (queryUser.isEmpty()) {
            throw new ResourceNotFoundException("User", "User ID", userId);
        }

        user = queryUser.get();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setContactNumber(userRequest.getContactNumber());
        user.setUserType(userRequest.getUserType());
        userRepository.save(user);
        return user;
    };

    @Override
    public User deleteUser(Integer userId) throws ResourceNotFoundException {
        User user = null;
        Optional<User> queryUser = userRepository.findById(userId);
        if (queryUser.isEmpty()) {
            throw new ResourceNotFoundException("User", "User ID", userId);
        }
        user = queryUser.get();
        user.setDefunct(true);
        userRepository.save(user);
        return user;
    }

    @Override
    public User registerUser(UserRequest userRequest) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void saveVerificationTokenForUser(String token, User user) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String validateVerificationToken(String token) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String validatePasswordResetToken(String token) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public void changePassword(User user, String newPassword) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean checkIfValidOldPassword(User user, String oldPassword) {
        // TODO Auto-generated method stub
        return false;
    }
}
