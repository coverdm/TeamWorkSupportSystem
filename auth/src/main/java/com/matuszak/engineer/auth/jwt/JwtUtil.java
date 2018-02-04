package com.matuszak.engineer.auth.jwt;

import java.time.Instant;

public class JwtUtil {

    public static final String SECRET_KEY = "MyOwnSecretKey";
    public static final Integer EXPIRATION_SECOND_AMOUNT = 100000;
    public static final Instant EXPIRATION_DATE = Instant.now().plusSeconds(EXPIRATION_SECOND_AMOUNT);
    public static final String PREFIX_AUTHENTICATION = "Bearer";
}
