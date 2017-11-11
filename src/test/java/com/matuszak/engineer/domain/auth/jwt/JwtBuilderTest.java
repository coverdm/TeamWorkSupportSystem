package com.matuszak.engineer.domain.auth.jwt;

import com.matuszak.engineer.Application;
import com.matuszak.engineer.domain.auth.jwt.JwtBuilder;
import com.matuszak.engineer.domain.auth.model.SecurityLevel;
import com.matuszak.engineer.domain.auth.model.entity.Subject;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
public class JwtBuilderTest {

    @Autowired
    JwtBuilder jwtBuilder;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void shouldReturnRawToken(){

        String email = "email@email.com";
        String password = "password";
        String username = "username";

        Subject subject = Subject.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .securityLevel(SecurityLevel.USER)
                .username(username)
                .enabled(true)
                .authorities(null)
                .build();

        String s = jwtBuilder.generateToken(subject);

        assertThat(s)
                .isNotNull()
                .isNotBlank()
                .isNotEmpty();
    }

}
