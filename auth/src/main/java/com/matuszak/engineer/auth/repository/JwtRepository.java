package com.matuszak.engineer.auth.repository;

import com.matuszak.engineer.auth.model.entity.Token;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface JwtRepository extends MongoRepository<Token, Long> {

    Optional<Token> getTokenByValue(String value);

}
