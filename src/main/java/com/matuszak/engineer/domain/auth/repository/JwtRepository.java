package com.matuszak.engineer.domain.auth.repository;

import com.matuszak.engineer.domain.auth.model.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JwtRepository extends JpaRepository<Token, Long>{

    Optional<Token> getTokenByValue(String value);

}
