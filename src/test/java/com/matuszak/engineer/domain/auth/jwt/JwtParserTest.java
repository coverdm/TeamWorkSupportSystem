package com.matuszak.engineer.domain.auth.jwt;

import com.matuszak.engineer.Application;
import com.matuszak.engineer.domain.auth.model.entity.Token;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
public class JwtParserTest {

    @MockBean JwtValidator jwtValidator;
    @Autowired JwtParser jwtParser;

    @Test
    public void shouldReturnOptionalOfAuthentication(){

        String jwtValue = "eyJhbGciOiJIUzI1NiJ9.eyJjbGFpbXMiOltdLCJzdWIiOi" +
                            "JqYWtpc3VzZXJuYW1lIiwiaWF0IjoxNTEwNDk1ODI2fQ.Tt9" +
                            "zMeT92rk-BjFnVq3vTVKEViTM7zX2sgbW6N6LEUI";

        Token token = new Token(jwtValue);

        Optional<Authentication> authentication = jwtParser.authenticationFromToken(token);

        assertThat(authentication.get()).isNotNull();
    }
}
