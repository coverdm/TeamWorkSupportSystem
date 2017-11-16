package com.matuszak.engineer.boundary.web;

import com.matuszak.engineer.Application;
import com.matuszak.engineer.domain.auth.model.dto.LoginModel;
import com.matuszak.engineer.domain.auth.model.entity.Token;
import lombok.extern.java.Log;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.login.LoginException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Log
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
public class AuthControllerTest {

    @Autowired
    RestTemplate restTemplate;

    private final String PORT = "8080";
    private final String LOCALHOST = "http://127.0.1.1";
    private final String PATH_LOGIN = "/api/auth/login";

    private final HttpHeaders httpHeaders = new HttpHeaders();

    @Before
    public void setUp(){
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void shouldReturnTokenAfterLogging() throws Exception{

        //given
        LoginModel loginModel = new LoginModel("admin@admin.com", "JakisPassword", "nieuzywanyUsername");
        HttpEntity<?> body = new HttpEntity<>(loginModel, this.httpHeaders);

        //do
        ResponseEntity<Map> authMap = restTemplate.exchange(LOCALHOST + ":" + PORT + PATH_LOGIN, HttpMethod.POST, body, Map.class);
        assertThat(authMap).isNotNull();
        assertThat(authMap.getBody().get("token")).isNotNull();
        assertThat(authMap.getBody().get("userId")).isNotNull();
    }

}
