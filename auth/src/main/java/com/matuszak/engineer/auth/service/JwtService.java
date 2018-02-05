package com.matuszak.engineer.auth.service;

import com.matuszak.engineer.auth.jwt.JwtBuilder;
import com.matuszak.engineer.auth.jwt.JwtParser;
import com.matuszak.engineer.auth.model.entity.Subject;
import com.matuszak.engineer.auth.model.entity.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Log
@RequiredArgsConstructor
@Service
public class JwtService {

    private final JwtParser jwtParser;
    private final JwtBuilder jwtBuilder;

    public void getAuthenticationFromRequest(HttpServletRequest httpServletRequest) {
        String accessToken = httpServletRequest.getHeader("Authorization");
        log.info("Access TOKEN: " + accessToken);
        jwtParser.authenticationFromToken(new Token(accessToken));
    }

    public Token createToken(Subject subject) {
        return new Token(jwtBuilder.generateToken(subject));
    }

    public void checkAuthentication(HttpServletRequest httpServletRequest){
        getAuthenticationFromRequest(httpServletRequest);
    }

    public void checkAuthenticationToken(String token){
        log.info("!!!!!!!!!!!TOKEN!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!: " + token);
        jwtParser.authenticationFromToken(new Token(token));
    }
}