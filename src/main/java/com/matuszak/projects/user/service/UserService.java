package com.matuszak.projects.user.service;

import com.matuszak.projects.user.dto.UserDTO;
import com.matuszak.projects.user.entity.User;

import java.util.Optional;

public interface UserService {

    Optional<User> getUserByEmail(String email);
    Optional<User> getUserByUsername(String username);

    void deleteUser(UserDTO user);

    User saveUser(User user);
    User updateUser(UserDTO user);
}
