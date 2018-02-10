package com.matuszak.engineer.auth.service;

import com.matuszak.engineer.auth.exceptions.IllegalJwtFormatException;
import com.matuszak.engineer.auth.exceptions.TokenHadBeenExpiredException;
import com.matuszak.engineer.auth.exceptions.UnknownTokenException;
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

    public Token createToken(Subject subject) {
        return new Token(jwtBuilder.generateToken(subject));
    }

    public void checkAuthorizationToken(Token token) throws NullPointerException, TokenHadBeenExpiredException, IllegalJwtFormatException, UnknownTokenException {
        jwtParser.parseToken(token);
    }

    public String getSubject(String accessToken) {
        return jwtParser.getSubject(accessToken);
    }
}