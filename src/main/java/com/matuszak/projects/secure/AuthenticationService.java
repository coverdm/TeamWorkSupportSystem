package com.matuszak.projects.secure;

import com.matuszak.projects.domain.User;
import com.matuszak.projects.repositories.UserRepository;
import com.matuszak.projects.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by dawid on 04.04.17.
 */
@Service
public class AuthenticationService {

    private Logger logger = Logger.getLogger(getClass().getName());
    private UserService userService;
    private static final String SECRET_KEY = "MyOwnSecretKey";

    @Autowired
    public AuthenticationService(final UserService userService) {
        this.userService = userService;
    }

    public Map<String, Object> authenticate(User user, HttpServletResponse response){

        User authUser = userService.getUserByUsername(user.getUsername());
        logger.info(authUser.getFirstName() + " " + authUser.getPassword() + " " + user.getPassword());

        if(isUserValid(user, authUser)) {
            return authentication(authUser, generateToken(authUser));
        }
        else
            try {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            } catch (IOException e) {
                e.printStackTrace();
            }
        return null;
    }

    private boolean isUserValid(User user, User authUser) {
        return authUser != null && isPasswordValid(user, authUser) && authUser.isEnabled();
    }

    private boolean isPasswordValid(User user, User authUser) {

        logger.info("isPasswordValid: " + authUser.getPassword().equals(user.getPassword()));

        return authUser.getPassword().equals(user.getPassword());
    }

    private String generateToken(User user){
        return Jwts.builder()
                .claim("claims", user.getUserRoles())
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
