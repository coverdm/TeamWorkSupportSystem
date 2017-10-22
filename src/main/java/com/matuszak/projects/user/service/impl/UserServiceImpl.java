package com.matuszak.projects.user.service.impl;

import com.matuszak.projects.user.dto.UserDTO;
import com.matuszak.projects.user.entity.User;
import com.matuszak.projects.user.repository.UserRepository;
import com.matuszak.projects.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> getUserByUsername(String username) {
        log.info("Getting an user by username...");
        return Optional.ofNullable(userRepository.getUserByUsername(username));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        log.info("Getting an user by email...");
        return Optional.ofNullable(userRepository.getUserByEmail(email));
    }

    @Override
    public User saveUser(User user) {
        log.info("Saving user...");
        return userRepository.save(user);
    }

    //TODO implement the method that deletes user
    @Override
    public void deleteUser(UserDTO user) {
        log.info("Deleting user...");
    }

    //TODO implement the method that updates user
    @Override
    public User updateUser(UserDTO user) {
        log.info("Updating user...");
        return null;
    }

}