package com.matuszak.projects.user;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();
    void saveUser(User user);
    Optional<User> getUserByUsername(String username);
    void deleteUser(User user);
    List<User> getUsersByUsername(List<String> usernames);
}
