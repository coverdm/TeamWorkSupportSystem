package com.matuszak.projects.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public User getUserByUsername(String username) throws UserNotFoundException{
        return userRepository.findUserByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
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
    public User changePassword(String newPassword) {

        User userDB = getUserByUsername(getLoggedUser());

        String encode = new StandardPasswordEncoder().encode(newPassword);

        userDB.setPassword(encode);

        return userDB;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    private String getLoggedUser(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
