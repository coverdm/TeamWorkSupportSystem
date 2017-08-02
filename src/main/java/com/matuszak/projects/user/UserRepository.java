package com.matuszak.projects.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findUserByUsername(String username);
    List<User> getUsersByUsernameIn(List<String> user);
}
