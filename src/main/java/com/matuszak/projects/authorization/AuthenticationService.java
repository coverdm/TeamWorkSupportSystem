package com.matuszak.projects.authorization;

import com.matuszak.projects.user.User;
import com.matuszak.projects.user.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by dawid on 04.04.17.
 */
@Service
public class AuthenticationService {

    private UserService userService;
    private static final String SECRET_KEY = "MyOwnSecretKey";

    @Autowired
    public AuthenticationService(final UserService userService) {
        this.userService = userService;
    }

    public Map<String, Object> authenticate(User user, HttpServletResponse response){

        Optional<User> userByUsername = userService.getUserByUsername(user.getUsername());

        if(userByUsername.isPresent()){
            User userDB = userByUsername.get();

            if(isUserValid(user, userDB)){
                return authentication(userDB, generateToken(userDB));
            }
            return null;
        }

        return null;
    }

    private boolean isUserValid(User user, User authUser) {
        return authUser != null && isPasswordValid(user, authUser) && authUser.isEnabled();
    }

    private boolean isPasswordValid(User user, User authUser) {
        return authUser.getPassword().equals(user.getPassword());
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
