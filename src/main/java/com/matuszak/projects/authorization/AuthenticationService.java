package com.matuszak.projects.authorization;

import com.matuszak.projects.user.User;
import com.matuszak.projects.user.UserNotFoundException;
import com.matuszak.projects.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.logging.Logger;

@Service
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final UserTokenAuthMap userTokenAuthMap;

    @Autowired
    public AuthenticationService(UserService userService,
                                 PasswordEncoder passwordEncoder,
                                 UserTokenAuthMap userTokenAuthMap) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userTokenAuthMap = userTokenAuthMap;
    }

    public Map<String, Object> authenticate(User user){

        try{
            User userDB = userService.getUserByUsername(user.getUsername());

            boolean matches = passwordEncoder.matches(user.getPassword(), userDB.getPassword());

            if(matches && userDB.isEnabled()){
                return userTokenAuthMap.createUserTokenMap(userDB);
            }

        }catch (UserNotFoundException e){
            e.printStackTrace();
        }

        return null;
    }
}
