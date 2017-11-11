package com.matuszak.engineer.domain.auth.jwt;

import com.matuszak.engineer.domain.auth.model.entity.Subject;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Log
public class JwtBuilder {

    public String generateToken(Subject subject){
        return Jwts.builder()
                .claim("claims", subject.getAuthorities())
                .setSubject(subject.getUsername())
                .setIssuedAt(Date.from(Instant.now().plusSeconds(JwtUtil.EXPIRATION_SECOND_AMOUNT)))
                .signWith(SignatureAlgorithm.HS256, JwtUtil.SECRET_KEY)
                .compact();
    }
}

