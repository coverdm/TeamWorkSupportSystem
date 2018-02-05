package com.matuszak.engineer.auth.jwt;

import com.matuszak.engineer.auth.model.entity.Subject;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Log
public class JwtBuilder {

    public String generateToken(Subject subject){
        return Jwts.builder()
                .claim("claims", Arrays.asList("ADMIN"))
                .setSubject(subject.getSubjectId())
                .setIssuedAt(Date.from(Instant.now().plusSeconds(JwtUtil.EXPIRATION_SECOND_AMOUNT)))
                .signWith(SignatureAlgorithm.HS256, JwtUtil.SECRET_KEY)
                .compact();
    }
}

