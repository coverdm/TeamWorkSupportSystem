package com.matuszak.projects.authorization;

import com.matuszak.projects.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

public class TokenGenerator {

    private static final String SECRET_KEY = "MyOwnSecretKey";

    public String generateToken(User user){
        return Jwts.builder()
                .claim("claims", user.getUserRole())
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis() + 100000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
