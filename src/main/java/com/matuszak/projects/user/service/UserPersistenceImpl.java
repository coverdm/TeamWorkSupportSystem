package com.matuszak.projects.user.service;

import com.matuszak.projects.user.repository.UserRepository;
import com.matuszak.projects.user.entity.User;
import com.matuszak.projects.user.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class UserPersistenceImpl implements UserPersistence {

    private final UserRepository userRepository;
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public UserPersistenceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
        logger.info("Saved user: " + user.getUsername());
    }

    @Override
    public User getUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public void deleteUser(User user) {
        this.userRepository.delete(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
