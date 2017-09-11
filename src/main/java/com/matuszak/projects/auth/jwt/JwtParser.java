package com.matuszak.projects.auth.jwt;

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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log
@Component
@RequiredArgsConstructor
public class JwtParser {

    private static final String SECRET_KEY = "MyOwnSecretKey";
    private static final String PREFIX_AUTHENTICATION = "Bearer";

    public Authentication authenticationFromToken(String accessToken){

        log.info("Parsing token...");

        String token = accessToken.replace(PREFIX_AUTHENTICATION, "");

        Claims claims = extractClaims(token);

        List<GrantedAuthority> simpleGrantedAuthorities = authorityListFromClaims(claims);
        User user = new User(claims.getSubject(), "", simpleGrantedAuthorities);

        return new UsernamePasswordAuthenticationToken(user, "", simpleGrantedAuthorities);
    }

    private Claims extractClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private List<GrantedAuthority> authorityListFromClaims(Claims claims){
        return Stream.of(claims.get("claims").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

    }
}