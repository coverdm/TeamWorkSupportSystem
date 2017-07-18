package com.matuszak.projects.user;

import com.matuszak.projects.authorization.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Created by dawid on 18.06.17.
 */

@RequestMapping("/api/user")
@RestController
public class UserController {

    private final UserService userService;
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/details")
    public ResponseEntity<User> getUserDetails(Principal principal) {
        Optional<User> user = userService.getUserByUsername(principal.getName());
        return new ResponseEntity<>(user.get(), HttpStatus.ACCEPTED);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(User user){
        this.userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/detele")
    public ResponseEntity<?> deleteUser(User user){
        this.userService.deleteUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(User user){
        this.userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/getAllUsers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<List<User>>(this.userService.getAllUsers(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/test")
    public ResponseEntity<List<User>> createNew(){

        User build = User.builder()
                .username("admin")
                .password("admin")
                .enabled(true)
                .email("admin@admin.com")
                .userRole(Role.ADMIN)
                .firstName("dawid")
                .lastName("matuszak")
                .build();

        User user = User.builder()
                .username("user")
                .password("user")
                .enabled(true)
                .email("userowo@mobile.com")
                .userRole(Role.USER)
                .firstName("kasia")
                .lastName("nowak")
                .build();

        this.userService.saveUser(build);
        this.userService.saveUser(user);

        return new ResponseEntity<>(Arrays.asList(build,user), HttpStatus.ACCEPTED);
    }

    @GetMapping("/test2")
    public ResponseEntity<?> responseEntity(){
        return new ResponseEntity<Object>(this.userService.getUsersByUsername(Arrays.asList("admin", "user")), HttpStatus.ACCEPTED);
    }
}