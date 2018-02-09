package com.matuszak.engineer.auth.jwt;

import com.matuszak.engineer.auth.exceptions.IllegalJwtFormatException;
import com.matuszak.engineer.auth.exceptions.UnknownTokenException;
import com.matuszak.engineer.auth.model.entity.Token;
import com.matuszak.engineer.auth.repository.JwtRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Log
public class JwtValidator {

    private final JwtRepository jwtRepository;

    public void isPrefixValid(String token) throws IllegalJwtFormatException{
        if(!token.contains(JwtUtil.PREFIX_AUTHENTICATION))
            throw new IllegalJwtFormatException(token + " is invalid");

    }

    public void isPresentInRepository(String token) throws UnknownTokenException{

        Token token1 = jwtRepository.getTokenByValue(token).orElseThrow(() -> new UnknownTokenException(""));

        log.info(token1.toString());

        if(!jwtRepository.getTokenByValue(token).isPresent()){
            throw new UnknownTokenException(token + " is not stored in app");
        }
    }
}
