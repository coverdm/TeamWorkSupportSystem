package com.matuszak.engineer.auth.service;

import com.matuszak.engineer.auth.jwt.JwtBuilder;
import com.matuszak.engineer.auth.jwt.JwtParser;
import com.matuszak.engineer.auth.model.entity.Subject;
import com.matuszak.engineer.auth.model.entity.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Log
@RequiredArgsConstructor
@Service
public class JwtService {

    private final JwtParser jwtParser;
    private final JwtBuilder jwtBuilder;

    public Optional<Authentication> getAuthenticationFromRequest(HttpServletRequest httpServletRequest) {
        String accessToken = httpServletRequest.getHeader("Authorization");
        log.info("Access TOKEN: " + accessToken);
        return jwtParser.authenticationFromToken(new Token(accessToken));
    }

    public Token createToken(Subject subject) {
        return new Token(jwtBuilder.generateToken(subject));
    }
}