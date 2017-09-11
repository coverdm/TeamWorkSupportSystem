package com.matuszak.projects.auth.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Log
@RequiredArgsConstructor
@Service
public class JwtService {

    private final JwtParser jwtParser;

    public Authentication getAuthenticationFromToken(String token){
        return jwtParser.authenticationFromToken(token);
    }

    public Authentication getAuthenticationFromRequest(HttpServletRequest httpServletRequest){
        return getAuthenticationFromToken(httpServletRequest.getHeader("Authorization"));
    }
}
