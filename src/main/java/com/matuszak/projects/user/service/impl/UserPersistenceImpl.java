package com.matuszak.projects.user.service.impl;

import com.matuszak.projects.user.repository.UserRepository;
import com.matuszak.projects.user.entity.User;
import com.matuszak.projects.user.exceptions.UserNotFoundException;
import com.matuszak.projects.user.service.UserPersistence;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Log
@RequiredArgsConstructor
public class UserPersistenceImpl implements UserPersistence {

    private final UserRepository userRepository;

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
        log.info("Saved user: " + user.getUsername());
    }

    @Override
    public Optional<User> getUserByUsername(String username) throws UserNotFoundException {
        return Optional.ofNullable(userRepository.findUserByUsername(username));
    }

    @Override
    public void deleteUser(User user) {
        this.userRepository.delete(user);
        log.info("Removed user: " + user.toString());
    }
}
