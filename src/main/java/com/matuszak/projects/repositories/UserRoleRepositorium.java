package com.matuszak.projects.repositories;

import com.matuszak.projects.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by dawid on 19.05.17.
 */
public interface UserRoleRepositorium extends JpaRepository<UserRole, Long>{
}
