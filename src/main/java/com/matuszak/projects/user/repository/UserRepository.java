package com.matuszak.projects.user.repository;

import com.matuszak.projects.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    User findUserByUsername(String username);
}
