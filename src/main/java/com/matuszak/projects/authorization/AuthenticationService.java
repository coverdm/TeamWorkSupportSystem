package com.matuszak.projects.authorization;

import com.matuszak.projects.user.User;
import com.matuszak.projects.user.UserNotFoundException;
import com.matuszak.projects.user.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = Logger.getLogger(getClass().getName());

    private static final String SECRET_KEY = "MyOwnSecretKey";

    @Autowired
    public AuthenticationService(final UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public Map<String, Object> authenticate(User user){

        try{
            User userDB = userService.getUserByUsername(user.getUsername());

            boolean matches = passwordEncoder.matches(user.getPassword(), userDB.getPassword());

            if(matches && userDB.isEnabled()){
                return authentication(userDB, generateToken(userDB));
            }

        }catch (UserNotFoundException e){
            e.printStackTrace();
        }

        return null;
    }


    private String generateToken(User user){
        return Jwts.builder()
                .claim("claims", user.getUserRole())
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis() + 100000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    private Map<String, Object> authentication(User current, String token){
        Map<String,Object> auth = new HashMap<>();
        auth.put("current", current);
        auth.put("token", token);
        return auth;
    }
}
