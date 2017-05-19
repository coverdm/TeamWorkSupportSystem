package com.matuszak.projects.services;

import com.matuszak.projects.domain.User;

/**
 * Created by dawid on 16.04.17.
 */
public interface UserService {
    void register(User user);
    User getUserByUsername(String username);
}
