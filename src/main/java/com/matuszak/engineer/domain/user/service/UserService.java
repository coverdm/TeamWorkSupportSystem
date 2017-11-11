package com.matuszak.engineer.domain.user.service;

import com.matuszak.engineer.domain.user.model.UserDTO;
import com.matuszak.engineer.domain.user.entity.User;
import com.matuszak.engineer.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public Optional<UserDTO> getUserByUsername(String username) {

        log.info("Getting an user by username...");

        return userRepository.getUserByUsername(username)
                .map(e -> modelMapper.map(e, UserDTO.class));
    }

    public Optional<UserDTO> getUserByEmail(String email) {

        log.info("Getting an user by email...");

        return userRepository.getUserByEmail(email)
                .map(e -> modelMapper.map(e , UserDTO.class));
    }

    //TODO implement the method that deletes user
    public void deleteUser(UserDTO userDTO) {
        log.info("Deleting user...");
        userRepository.getUserByEmail(userDTO.getEmail())
                .ifPresent(userRepository::delete);
    }

    public User saveUser(User user) {
        return null;
    }

    public User saveUser(UserDTO userDTO) {

        log.info("Saving user...");

        User build = User.builder()
                .email(userDTO.getEmail())
                .build();

        return userRepository.save(build);
    }

    //TODO implement the method that updates user
    public User updateUser(UserDTO user) {
        log.info("Updating user...");
        return null;
    }

}
