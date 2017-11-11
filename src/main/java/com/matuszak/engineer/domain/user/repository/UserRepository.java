package com.matuszak.engineer.domain.user.repository;

import com.matuszak.engineer.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByEmail(String email);
}
