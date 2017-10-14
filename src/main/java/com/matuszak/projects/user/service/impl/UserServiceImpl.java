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
        return Optional.ofNullable(userRepository.getUserByUsername(username));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(userRepository.getUserByEmail(email));
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(UserDTO user) {

    }

    @Override
    public User updateUser(UserDTO user) {
        return null;
    }

}