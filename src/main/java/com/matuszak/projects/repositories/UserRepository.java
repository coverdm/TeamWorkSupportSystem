package com.matuszak.projects.repositories;

import com.matuszak.projects.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by dawid on 21.03.17.
 */
public interface UserRepository extends JpaRepository<User, Long>{

    User findUserByUsername(String username);

}
