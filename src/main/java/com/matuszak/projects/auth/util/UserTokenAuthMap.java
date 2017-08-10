package com.matuszak.projects.auth.util;

import com.matuszak.projects.user.entity.User;

import java.util.HashMap;
import java.util.Map;

public class UserTokenAuthMap {

    private final Map<String,Object> userTokenAuth;
    private final TokenGenerator tokenGenerator;

    public UserTokenAuthMap(TokenGenerator tokenGenerator) {
        this.tokenGenerator = tokenGenerator;
        this.userTokenAuth = new HashMap<>();
    }

    public Map<String, Object> createUserTokenMap(User authenticatedUser){

        if(!userTokenAuth.isEmpty())
            userTokenAuth.clear();

        userTokenAuth.put("user", authenticatedUser);
        userTokenAuth.put("token", tokenGenerator.generateToken(authenticatedUser));

        return userTokenAuth;
    }
}
