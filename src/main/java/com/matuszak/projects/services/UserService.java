package com.matuszak.projects.services;

import com.matuszak.projects.domain.User;

import java.util.List;

/**
 * Created by dawid on 16.04.17.
 */
public interface UserService {

    List<User> getAllUsers();

    void save(User user);
    User getUserByUsername(String username);
}
