package com.matuszak.engineer.domain.auth.service;

import com.matuszak.engineer.Application;
import com.matuszak.engineer.domain.auth.exceptions.EmailAlreadyExistsException;
import com.matuszak.engineer.domain.auth.exceptions.PasswordNotMatchedException;
import com.matuszak.engineer.domain.auth.model.SecurityLevel;
import com.matuszak.engineer.domain.auth.model.dto.RegisterModel;
import com.matuszak.engineer.domain.auth.model.entity.Subject;
import com.matuszak.engineer.domain.auth.repository.SubjectRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = Application.class)
@RunWith(SpringRunner.class)
public class RegistrationServiceTest {

    @Autowired RegistrationService registrationService;
    @Autowired PasswordEncoder passwordEncoder;
    @MockBean  SubjectRepository subjectRepository;

    private final String EMAIL = "admin@admin.com";
    private final String USERNAME = "someUsername";
    private final String PASSWORD = "somePassword";

    @Test
    public void shouldRegisterProperly() throws Exception{

        RegisterModel registerModel = RegisterModel.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .confirmPassword(PASSWORD)
                .username(USERNAME)
                .build();

        Subject subject = Subject.builder()
                .email(registerModel.getEmail())
                .password(passwordEncoder.encode(registerModel.getPassword()))
                .username(registerModel.getUsername())
                .securityLevel(SecurityLevel.USER)
                .authorities(null)
                .build();

        when(subjectRepository.getSubjectByEmail(EMAIL))
                .thenReturn(Optional.ofNullable(null));

        registrationService.register(registerModel);
    }


    @Test
    public void shouldThrowPasswordNotMatchedException(){

        final String SECOND_PASSWORD = "secondPassword";

        RegisterModel registerModel = RegisterModel.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .confirmPassword(SECOND_PASSWORD)
                .username(USERNAME)
                .build();

        assertThatExceptionOfType(PasswordNotMatchedException.class)
                .isThrownBy(() -> registrationService.register(registerModel));
    }

    @Test
    public void shouldThrowExceptionWhenEmailAlreadyExists(){

        RegisterModel registerModel = RegisterModel.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .confirmPassword(PASSWORD)
                .username(USERNAME)
                .build();

        Subject subject = Subject.builder()
                .email(EMAIL)
                .password(passwordEncoder.encode(PASSWORD))
                .username(USERNAME)
                .securityLevel(SecurityLevel.USER)
                .authorities(null)
                .build();

        when(subjectRepository.getSubjectByEmail(EMAIL))
                .thenReturn(Optional.ofNullable(subject));

        assertThatExceptionOfType(EmailAlreadyExistsException.class)
                .isThrownBy(() -> registrationService.register(registerModel));
    }
}
