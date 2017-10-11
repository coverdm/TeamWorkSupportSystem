package com.matuszak.projects.user.service;

import com.matuszak.projects.user.entity.User;

import java.util.Optional;

public interface UserService {
//    User registerNewUser(UserDTO userDTO) throws EmailAlreadyExistsException;

//    User registerNewUser(RegisterModel registerModel) throws EmailAlreadyExistsException;

//    UserDTO getUserByUsername(String username) throws UserNotFoundException;
//    UserDTO getUserByEmail(String email) throws UserNotFoundException;

//    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByEmail(String email);

    User saveUser(User user);
}
