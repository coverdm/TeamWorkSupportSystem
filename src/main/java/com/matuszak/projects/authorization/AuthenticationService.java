package com.matuszak.projects.authorization;

import com.matuszak.projects.user.User;
import com.matuszak.projects.user.UserNotFoundException;
import com.matuszak.projects.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final TokenGenerator tokenGenerator;

    @Autowired
    public AuthenticationService(UserService userService,
                                 PasswordEncoder passwordEncoder,
                                 TokenGenerator tokenGenerator) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.tokenGenerator = tokenGenerator;
    }

    public Map<String, Object> authenticate(User user){

        try{
            User userDB = userService.getUserByUsername(user.getUsername());

            boolean matches = passwordEncoder.matches(user.getPassword(), userDB.getPassword());

            if(matches && userDB.isEnabled()){
                return createTokenUserResponse(userDB, tokenGenerator.generateToken(user));
            }

        }catch (UserNotFoundException e){
            e.printStackTrace();
        }

        return null;
    }

    private Map<String, Object> createTokenUserResponse(User authenticatedUser, String token){
        Map<String,Object> auth = new HashMap<>();
        auth.put("user", authenticatedUser);
        auth.put("token", token);
        return auth;
    }
}
