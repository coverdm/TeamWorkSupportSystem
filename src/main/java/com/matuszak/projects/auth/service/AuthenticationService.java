package com.matuszak.projects.auth.service;

import com.matuszak.projects.auth.util.UserTokenAuthMap;
import com.matuszak.projects.user.entity.User;
import com.matuszak.projects.user.exceptions.UserNotFoundException;
import com.matuszak.projects.user.service.UserPersistence;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Log
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserPersistence userPersistence;
    private final PasswordEncoder passwordEncoder;
    private final UserTokenAuthMap userTokenAuthMap;

    public Map<String, Object> authenticate(User user){

        log.info("Authenticating user...");

        Optional<User> userDB = userPersistence.getUserByUsername(user.getUsername())
                .filter(e -> passwordEncoder.matches(user.getPassword(), e.getPassword()))
                .filter(e -> e.isEnabled());

        return userTokenAuthMap.createUserTokenMap(userDB.orElseThrow(() -> new UserNotFoundException("No a such user")));
    }
}