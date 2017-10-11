package com.matuszak.projects.auth.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Log
@RequiredArgsConstructor
@Service
public class JwtService {

    private final JwtParser jwtParser;

    public Authentication getAuthenticationFromToken(String token){
        return jwtParser.authenticationFromToken(token);
    }

    public Optional<Authentication> getAuthenticationFromRequest(HttpServletRequest httpServletRequest){

        String accessToken = httpServletRequest.getHeader("Authorization");

        log.info("Authorization: " + accessToken);

        Optional<Authentication> authentication = Optional.ofNullable(accessToken).map(e -> getAuthenticationFromToken(e));

        return authentication;
    }
}