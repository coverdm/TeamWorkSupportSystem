package com.matuszak.projects.user.service;

import com.matuszak.projects.user.entity.User;
import com.matuszak.projects.user.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

@Deprecated
public interface UserPersistence {
    void saveUser(User user);
    Optional<User> getUserByUsername(String username);
    void deleteUser(User user);
}
