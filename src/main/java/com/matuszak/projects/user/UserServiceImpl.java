package com.matuszak.projects.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
        logger.info("Saved user: " + user.getUsername());
    }

    @Override
    public Optional<User> getUserByUsername(String username){
        return userRepository.findUserByUsername(username);
    }

    @Override
    public void deleteUser(User user) {
        this.userRepository.delete(user);
    }

    @Override
    public List<User> getUsersByUsername(List<String> usernames) {
        return this.userRepository.getUsersByUsernameIn(usernames);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
