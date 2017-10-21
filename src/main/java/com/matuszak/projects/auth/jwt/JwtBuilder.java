package com.matuszak.projects.auth.jwt;

import com.matuszak.projects.user.dto.UserDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtBuilder {

    public String generateToken(UserDTO user){
        return Jwts.builder()
                .claim("claims", user.getUserSecurityRole())
                .setSubject(user.getUsername())
                .setIssuedAt(Date.from(Instant.now().plusSeconds(JwtUtil.EXPIRATION_SECOND_AMOUNT)))
                .signWith(SignatureAlgorithm.HS256, JwtUtil.SECRET_KEY)
                .compact();
    }
}

