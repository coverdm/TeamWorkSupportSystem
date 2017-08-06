package com.matuszak.projects.auth;

import com.matuszak.projects.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class TokenGenerator {

    private static final String SECRET_KEY = "MyOwnSecretKey";

    public String generateToken(User user){
        return Jwts.builder()
                .claim("claims", user.getUserRole())
                .setSubject(user.getUsername())
                .setIssuedAt(expirationDate())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    private Date expirationDate(){
        return new Date(System.currentTimeMillis() + 100000);
    }
}
