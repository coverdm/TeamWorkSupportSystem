package com.matuszak.projects.auth.service;

import com.matuszak.projects.auth.util.UserTokenAuthMap;
import com.matuszak.projects.user.entity.User;
import com.matuszak.projects.user.service.UserPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.logging.Logger;

@Service
public class AuthService {

    private final UserPersistence userPersistence;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final UserTokenAuthMap userTokenAuthMap;

    @Autowired
    public AuthService(UserPersistence userPersistence,
                       PasswordEncoder passwordEncoder,
                       UserTokenAuthMap userTokenAuthMap) {
        this.userPersistence = userPersistence;
        this.passwordEncoder = passwordEncoder;
        this.userTokenAuthMap = userTokenAuthMap;
    }

    public Map<String, Object> authenticate(User user){

        User userDB = userPersistence.getUserByUsername(user.getUsername());

        boolean matches = passwordEncoder.matches(user.getPassword(), userDB.getPassword());

        if(matches && userDB.isEnabled()){
            return userTokenAuthMap.createUserTokenMap(userDB);
        }

        return null;
    }
}
