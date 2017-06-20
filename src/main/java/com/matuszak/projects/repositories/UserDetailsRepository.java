package com.matuszak.projects.repositories;

import com.matuszak.projects.domain.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by dawid on 18.06.17.
 */
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long>{
}
