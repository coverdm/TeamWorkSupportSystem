package com.matuszak.projects.services;

import com.matuszak.projects.project.ProjectRepository;
import com.matuszak.projects.user.UserRepository;
import com.matuszak.projects.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by dawid on 24.06.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ProjectRepository projectRepository;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() {
    }

    @Test
    public void shouldReturnTheSameValue() {
    }

}
