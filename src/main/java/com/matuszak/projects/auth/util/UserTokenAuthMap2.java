package com.matuszak.projects.auth.util;

import com.matuszak.projects.auth.jwt.JwtBuilder2;
import com.matuszak.projects.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Log
public class UserTokenAuthMap2 {

    private final JwtBuilder2 jwtBuilder;

    public Map<String, Object> createUserTokenMap(UserDTO authenticatedUser){

        log.info("Building response...");

        Map<String, Object> userTokenAuth = new HashMap<>();

        userTokenAuth.put("user", authenticatedUser);
        userTokenAuth.put("token", jwtBuilder.generateToken(authenticatedUser));

        return userTokenAuth;
    }

}
