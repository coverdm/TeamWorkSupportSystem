package com.matuszak.projects.user;

import com.matuszak.projects.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by dawid on 21.03.17.
 */
public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findUserByUsername(String username);

    List<User> getUsersByUsernameIn(List<String> user);
}
