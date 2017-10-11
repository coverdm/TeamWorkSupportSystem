package com.matuszak.projects.user.service;

import com.matuszak.projects.user.entity.User;
import com.matuszak.projects.user.repository.UserRepository2;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log
public class UserServiceImpl implements UserService {

    private final UserRepository2 userRepository;

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(userRepository.getUserByEmail(email));
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}