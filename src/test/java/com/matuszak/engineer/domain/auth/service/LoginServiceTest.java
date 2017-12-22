package com.matuszak.engineer.domain.auth.service;

import com.matuszak.engineer.Application;
import com.matuszak.engineer.domain.auth.model.SecurityLevel;
import com.matuszak.engineer.domain.auth.model.SubjectId;
import com.matuszak.engineer.domain.auth.model.dto.LoginModel;
import com.matuszak.engineer.domain.auth.model.entity.Subject;
import com.matuszak.engineer.domain.auth.model.entity.Token;
import com.matuszak.engineer.domain.auth.repository.JwtRepository;
import com.matuszak.engineer.domain.auth.repository.SubjectRepository;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.security.auth.login.LoginException;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@Log
@SpringBootTest
@ContextConfiguration(classes = Application.class)
@RunWith(SpringRunner.class)
public class LoginServiceTest {

    @Autowired LoginService loginService;
    @Autowired PasswordEncoder passwordEncoder;

    @MockBean SubjectRepository subjectRepository;
    @MockBean JwtRepository jwtRepository;
    @MockBean JwtService jwtService;

    private final String EMAIL = "admin@admin.com";
    private final String USERNAME = "someUsername";
    private final String PASSWORD = "somePassword";

    @Test
    public void shouldReturnTokenWhenLoginProcessWentCorrectly() throws Exception{

        Token token = new Token("someValuableContent");

        LoginModel loginModel = LoginModel.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .username(USERNAME)
                .build();

        Subject subject = Subject.builder()
                .subjectId(new SubjectId(EMAIL))
                .password(passwordEncoder.encode(PASSWORD))
                .securityLevel(SecurityLevel.USER)
                .username(USERNAME)
                .enabled(true)
                .authorities(null)
                .build();

        when(subjectRepository.getSubjectBySubjectId(new SubjectId(loginModel.getEmail())))
                .thenReturn(Optional.ofNullable(subject));

        when(jwtService.createToken(subject))
                .thenReturn(token);

        when(jwtRepository.save(token))
                .thenReturn(token);

        Map<String, Object> auth = loginService.login(loginModel);

        assertThat(auth).isNotNull();
    }

    @Test
    public void shouldThrowLoginExceptionWhenPasswordIsNotCorrect(){

        String password2 = "p@55w0rd";

        LoginModel loginModel = LoginModel.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .username(USERNAME)
                .build();

        Subject subject = Subject.builder()
                .subjectId(new SubjectId(EMAIL))
                .password(passwordEncoder.encode(password2))
                .securityLevel(SecurityLevel.USER)
                .username(USERNAME)
                .enabled(true)
                .authorities(null)
                .build();

        when(subjectRepository.getSubjectBySubjectId(new SubjectId(EMAIL)))
                .thenReturn(Optional.ofNullable(subject));

        assertThatExceptionOfType(LoginException.class)
                .isThrownBy(() -> loginService.login(loginModel));
    }

    @Test
    public void shouldThrowLoginExceptionWhenSubjectIsDisabled(){

        Boolean isEnabled = false;

        LoginModel loginModel = LoginModel.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .username(USERNAME)
                .build();

        Subject subject = Subject.builder()
                .subjectId(new SubjectId(EMAIL))
                .password(passwordEncoder.encode(PASSWORD))
                .securityLevel(SecurityLevel.USER)
                .username(USERNAME)
                .enabled(isEnabled)
                .authorities(null)
                .build();

        when(subjectRepository.getSubjectBySubjectId(new SubjectId(loginModel.getEmail())))
                .thenReturn(Optional.ofNullable(subject));

        assertThatExceptionOfType(LoginException.class)
                .isThrownBy(() -> loginService.login(loginModel));
    }
}