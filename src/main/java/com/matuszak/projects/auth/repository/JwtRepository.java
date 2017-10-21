package com.matuszak.projects.auth.repository;

import com.matuszak.projects.auth.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JwtRepository extends JpaRepository<Token, Long>{

    Optional<Token> getTokenByValue(String value);

}
