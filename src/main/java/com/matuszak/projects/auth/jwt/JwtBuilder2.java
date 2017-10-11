package com.matuszak.projects.auth.jwt;

import com.matuszak.projects.user.dto.UserDTO;
import com.matuszak.projects.user.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtBuilder2 {

    private static final String SECRET_KEY = "MyOwnSecretKey";

    public String generateToken(UserDTO user){
        return Jwts.builder()
                .claim("claims", user.getUserSecurityRole())
                .setSubject(user.getUsername())
                .setIssuedAt(expirationDate())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    private Date expirationDate(){
        return new Date(System.currentTimeMillis() + 100000);
    }
}

