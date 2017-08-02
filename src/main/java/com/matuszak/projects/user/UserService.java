package com.matuszak.projects.user;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();
    void saveUser(User user);
    User getUserByUsername(String username) throws UserNotFoundException;
    void deleteUser(User user);
    User changePassword(String newPassword);
}
