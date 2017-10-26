package com.matuszak.projects.auth.jwt;

import com.matuszak.projects.auth.repository.JwtRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Log
public class JwtValidator {

    private final JwtRepository jwtRepository;

    public boolean valid(String token){
        return isPrefixValid(token) && isPresentInRepository(token);
    }

    public boolean isPrefixValid(String token){
        log.info("Checking does token has proper prefix...");
        return token.contains(JwtUtil.PREFIX_AUTHENTICATION);
    }

    public boolean isPresentInRepository(String token) {
        log.info("Checking does token exists in repository...");
        return jwtRepository.getTokenByValue(token).isPresent();
    }
}
