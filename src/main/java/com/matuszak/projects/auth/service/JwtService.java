package com.matuszak.projects.auth.service;

import com.matuszak.projects.auth.domain.Token;
import com.matuszak.projects.auth.jwt.JwtBuilder;
import com.matuszak.projects.auth.jwt.JwtParser;
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

//TODO to ma byc tylko fasada -> nie ma być logiki zmieniającej stan obiektu
//Nie spełnia zasady Single Responsibility Principle
public class JwtService {

    private final JwtParser jwtParser;
    private final JwtRepository jwtRepository;
    private final JwtBuilder jwtBuilder;

    public Optional<Authentication> getAuthenticationFromRequest(HttpServletRequest httpServletRequest) {

        String accessToken = httpServletRequest.getHeader("Authorization");

        log.info("Authorization: " + accessToken);

        String temporaryToken = accessToken.replace("Bearer", "").trim();

        log.info("Jest w bazie?: " + String.valueOf(isPresentInRepository(temporaryToken)));

        log.info("Is token expired?: " + isTokenExpired(new Token(temporaryToken)));

        return Optional.ofNullable(temporaryToken)
                .filter(e -> isPresentInRepository(e))
                .map(Token::new)
                .map(e -> jwtParser.authenticationFromToken(e));

    }

    private boolean isPresentInRepository(String token) {
        return jwtRepository.getTokenByValue(token).isPresent();
    }

    public Token createToken(UserDTO userDTO) {
        return new Token(jwtBuilder.generateToken(userDTO));
    }

    public boolean isTokenExpired(Token token) {
        return jwtParser.isTokenExpired(token);
    }
}