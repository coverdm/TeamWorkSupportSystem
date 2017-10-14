package com.matuszak.projects.auth.util;

import com.matuszak.projects.auth.jwt.JwtBuilder;
import com.matuszak.projects.user.dto.UserDTO;
import com.matuszak.projects.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Log
@Component
@RequiredArgsConstructor
public class UserTokenAuthMap {

    private final JwtBuilder jwtBuilder;

    public Map<String, Object> createUserTokenMap(UserDTO authenticatedUser){

        log.info("Creating response...");

        Map<String, Object> userTokenAuth = new HashMap<>();

        userTokenAuth.put("user", authenticatedUser);
        userTokenAuth.put("token", jwtBuilder.generateToken(authenticatedUser));

        return userTokenAuth;
    }
}
