package com.matuszak.projects.auth.service;

import com.matuszak.projects.auth.domain.Token;
import com.matuszak.projects.auth.jwt.JwtBuilder;
import com.matuszak.projects.auth.jwt.JwtParser;
import com.matuszak.projects.auth.jwt.JwtValidator;
import com.matuszak.projects.auth.repository.JwtRepository;
import com.matuszak.projects.user.dto.UserDTO;
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
        return jwtParser.authenticationFromToken(new Token(accessToken));
    }

    public Token createToken(UserDTO userDTO) {
        return new Token(jwtBuilder.generateToken(userDTO));
    }
}