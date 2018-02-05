package com.matuszak.engineer.auth.jwt;

import com.matuszak.engineer.auth.model.entity.Token;
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
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log
@Component
@RequiredArgsConstructor
public class JwtParser {

    private final JwtValidator jwtValidator;

    public Optional<Authentication> authenticationFromToken(Token accessToken){

        log.info("Parsing token...");

        Optional<Claims> claims = Optional.ofNullable(accessToken.getValue())
                .filter(jwtValidator::isPrefixValid)
                .map(removeAuthorizationPrefix())
                .map(String::trim)
                .filter(jwtValidator::isPresentInRepository)
                .filter(JwtParser::isTokenExpired)
                .map(JwtParser::extractClaims);

        claims.ifPresentOrElse(e -> log.info(e.getSubject().toString()), () -> log.info("Nieistnieje"));

        Optional<List<GrantedAuthority>> grantedAuthorities = claims.map(this::authorityListFromClaims);

        return claims.map(Claims::getSubject)
                .map(e -> new User(e, "", grantedAuthorities.get()))
                .map(e -> new UsernamePasswordAuthenticationToken(e, "", e.getAuthorities()));
    }

    private static boolean isTokenExpired(String e) {

        boolean before = Instant.now().isBefore(extractExpirationTime(e));

        log.info(Boolean.toString(before));

        return before;
    }

    private Function<String, String> removeAuthorizationPrefix() {
        return e -> e.replace(JwtUtil.PREFIX_AUTHENTICATION, "");
    }

    private static Claims extractClaims(String token) {

        log.info(token);

        Claims body = Jwts
                .parser()
                .setSigningKey(JwtUtil.SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        log.info(body.toString());

        return body;
    }

    private static Instant extractExpirationTime(String token){
        return extractClaims(token)
                .getIssuedAt()
                .toInstant();
    }

    private List<GrantedAuthority> authorityListFromClaims(Claims claims){
        return Stream.of(claims.get("claims").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}