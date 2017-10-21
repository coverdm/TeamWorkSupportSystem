package com.matuszak.projects.auth.jwt;

import com.matuszak.projects.auth.domain.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log
@Component
@RequiredArgsConstructor
public class JwtParser {

    public Authentication authenticationFromToken(Token accessToken){

        log.info("Parsing token...");
        log.info("Token: " + accessToken.getValue());

        Claims claims = extractClaims(accessToken.getValue());

        List<GrantedAuthority> simpleGrantedAuthorities = authorityListFromClaims(claims);
        User user = new User(claims.getSubject(), "", simpleGrantedAuthorities);

        return new UsernamePasswordAuthenticationToken(user, "", simpleGrantedAuthorities);
    }

    private Claims extractClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(JwtUtil.SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private List<GrantedAuthority> authorityListFromClaims(Claims claims){
        return Stream.of(claims.get("claims").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

    }

    public boolean isTokenExpired(Token token){

        Date issuedAt = extractClaims(token.getValue())
                .getIssuedAt();

        log.info(issuedAt.toInstant().toString());

        return !Instant.now().isBefore(issuedAt.toInstant());
    }

}