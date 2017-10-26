package com.matuszak.projects.auth.service;

import com.matuszak.projects.auth.domain.LoginModel;
import com.matuszak.projects.auth.domain.Token;
import com.matuszak.projects.auth.repository.JwtRepository;
import com.matuszak.projects.user.dto.UserDTO;
import com.matuszak.projects.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@RequiredArgsConstructor
@Service
@Log
public class LoginService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final JwtRepository jwtRepository;

    public Token login(LoginModel loginModel) throws AuthenticationException {
        log.info("Authentication process...");

        UserDTO authenticatedUser = userService.getUserByEmail(loginModel.getEmail())
                .filter(e -> passwordEncoder.matches(loginModel.getPassword(), e.getPassword()))
                .filter(e -> e.isEnabled())
                .map(e -> modelMapper.map(e, UserDTO.class))
                .orElseThrow(AuthenticationException::new);

        return jwtRepository.save(jwtService.createToken(authenticatedUser));
    }
}