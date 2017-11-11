package com.matuszak.engineer.boundary.web;

import com.matuszak.engineer.domain.user.entity.User;
import com.matuszak.engineer.domain.user.model.UserDTO;
import com.matuszak.engineer.domain.user.exceptions.UserNotFoundException;
import com.matuszak.engineer.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
@Log
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("/details")
    public ResponseEntity<UserDTO> getUserDetails(Principal principal) {

        Optional<UserDTO> userDTO = userService.getUserByUsername(principal.getName())
                .map(e -> modelMapper.map(e, UserDTO.class));
        
        return new ResponseEntity<>(userDTO.orElseThrow(UserNotFoundException::new), HttpStatus.ACCEPTED);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUser(UserDTO user){

        User updateUser = userService.updateUser(user);
        UserDTO userDTO = modelMapper.map(updateUser, UserDTO.class);

        return new ResponseEntity<>(userDTO, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/detele")
    public ResponseEntity<?> deleteUser(UserDTO user){
        userService.deleteUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}