package com.matuszak.projects.user.repository;

import com.matuszak.projects.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository2 extends JpaRepository<User, Long> {
    Optional<User> getUserByUsername(String username);
    User getUserByEmail(String email);
}
