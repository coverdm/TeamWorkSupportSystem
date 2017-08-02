package com.matuszak.projects.authorization;

import com.matuszak.projects.user.User;
import com.matuszak.projects.user.UserNotFoundException;
import com.matuszak.projects.user.UserPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.logging.Logger;

@Service
public class AuthenticationService {

    private final UserPersistence userPersistence;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final UserTokenAuthMap userTokenAuthMap;

    @Autowired
    public AuthenticationService(UserPersistence userPersistence,
                                 PasswordEncoder passwordEncoder,
                                 UserTokenAuthMap userTokenAuthMap) {
        this.userPersistence = userPersistence;
        this.passwordEncoder = passwordEncoder;
        this.userTokenAuthMap = userTokenAuthMap;
    }

    public Map<String, Object> authenticate(User user){

        try{
            User userDB = userPersistence.getUserByUsername(user.getUsername());

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
