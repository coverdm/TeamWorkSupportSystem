package com.matuszak.projects.auth.util;

import com.matuszak.projects.auth.jwt.JwtBuilder;
import com.matuszak.projects.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserTokenAuthMap {

    private final JwtBuilder jwtBuilder;

    public Map<String, Object> createUserTokenMap(User authenticatedUser){

        Map<String, Object> userTokenAuth = new HashMap<>();

        userTokenAuth.put("user", authenticatedUser);
        userTokenAuth.put("token", jwtBuilder.generateToken(authenticatedUser));

        return userTokenAuth;
    }
}
