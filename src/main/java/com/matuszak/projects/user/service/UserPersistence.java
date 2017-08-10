package com.matuszak.projects.user.service;

import com.matuszak.projects.user.entity.User;
import com.matuszak.projects.user.exceptions.UserNotFoundException;

import java.util.List;

public interface UserPersistence {

    List<User> getAllUsers();
    void saveUser(User user);
    User getUserByUsername(String username) throws UserNotFoundException;
    void deleteUser(User user);
}
