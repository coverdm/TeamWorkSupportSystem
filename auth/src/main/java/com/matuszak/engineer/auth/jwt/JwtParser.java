package com.matuszak.engineer.auth.jwt;

import com.matuszak.engineer.auth.exceptions.IllegalJwtFormatException;
import com.matuszak.engineer.auth.exceptions.TokenHadBeenExpiredException;
import com.matuszak.engineer.auth.exceptions.UnknownTokenException;
import com.matuszak.engineer.auth.model.entity.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Log
@Component
@RequiredArgsConstructor
public class JwtParser {

    private final JwtValidator jwtValidator;

    public void parseToken(Token accessToken) throws NullPointerException, TokenHadBeenExpiredException, IllegalJwtFormatException, UnknownTokenException {
        Optional.ofNullable(accessToken.getValue())
                .ifPresentOrElse(token -> {
                    jwtValidator.isPrefixValid(token);

                    log.info("##############################################################");
                    log.info(token);

                    token = token.replace(JwtUtil.PREFIX_AUTHENTICATION, "").trim();
//                    jwtValidator.isPresentInRepository(token);
//                    isTokenExpired(token);

                    log.info(token);
                    log.info("##############################################################");
                }, NullPointerException::new);
    }

    private void isTokenExpired(String e) {
        if(Instant.now().isBefore(extractExpirationTime(e))){
            throw new TokenHadBeenExpiredException();
        }
    }

    private static Claims extractClaims(String token) {

        Claims body = Jwts
                .parser()
                .setSigningKey(JwtUtil.SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return body;
    }

    private static Instant extractExpirationTime(String token){
        return extractClaims(token)
                .getIssuedAt()
                .toInstant();
    }
}