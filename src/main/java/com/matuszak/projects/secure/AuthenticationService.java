package com.matuszak.projects.secure;

import com.matuszak.projects.domain.User;
import com.matuszak.projects.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * Created by dawid on 04.04.17.
 */
@Service
public class AuthenticationService {

    private Logger logger = Logger.getLogger(getClass().getName());
    private UserRepository userRepository;

    @Autowired
    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String authenticate(User user, HttpServletResponse response){

        User authUser = userRepository.findUserByUsername(user.getUsername());

        if(authUser != null && authUser.getPassword().equals(user.getPassword()) && authUser.isEnabled())
            return generateToken(authUser);
        else
            try {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            } catch (IOException e) {
                e.printStackTrace();
            }
        return null;
    }

    private String generateToken(User user){

        user.getUserRoles().stream().forEach(e->logger.info("ROLE: " + e.getRole()));

        return Jwts.builder()
                .claim("claims", user.getUserRoles())
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis() + 100000))
                .signWith(SignatureAlgorithm.HS256, "someSicretKey")
                .compact();
    }
}
