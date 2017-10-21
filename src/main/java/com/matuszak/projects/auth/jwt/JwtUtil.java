package com.matuszak.projects.auth.jwt;

import org.springframework.stereotype.Component;

import java.time.Instant;

// I didnt know how to name it properly :)
public class JwtUtil {

    public static final String SECRET_KEY = "MyOwnSecretKey";
    public static final Integer EXPIRATION_SECOND_AMOUNT = 100;
    public static final Instant EXPIRATION_DATE = Instant.now().plusSeconds(EXPIRATION_SECOND_AMOUNT);
    public static final String PREFIX_AUTHENTICATION = "Bearer";

}
