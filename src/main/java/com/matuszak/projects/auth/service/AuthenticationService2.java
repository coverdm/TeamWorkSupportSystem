package com.matuszak.projects.auth.service;

import com.matuszak.projects.auth.domain.LoginModel;
import com.matuszak.projects.auth.util.UserTokenAuthMap;
import com.matuszak.projects.user.entity.User;
import com.matuszak.projects.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Log
public class AuthenticationService2 {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserTokenAuthMap userTokenAuthMap;

    public Map<String, Object> authenticate(LoginModel loginModel) throws AuthenticationException {

        log.info("Authentication process...");

        User user = userService.getUserByEmail(loginModel.getEmail())
                .filter(e -> passwordEncoder.matches(loginModel.getPassword(), e.getPassword()))
                .filter(e -> e.isEnabled())
                .orElseThrow(AuthenticationException::new);

        return userTokenAuthMap.createUserTokenMap(user);
    }
}