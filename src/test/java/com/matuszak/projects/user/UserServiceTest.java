package com.matuszak.projects.user;

import com.matuszak.projects.Application;
import com.matuszak.projects.TestConfiguration;
import com.matuszak.projects.authorization.Role;
import com.matuszak.projects.util.ProgrammingLanguages;
import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, TestConfiguration.class})
public class UserServiceTest {

    @MockBean UserRepository userRepository;
    @Autowired UserService userService;

    private static User user1, user2;

    @BeforeClass
    public static void setUp(){
        user1 = User.builder()
                .username("first")
                .password("7B7BC2512EE1FEDCD76BDC68926D4F7B")
                .email("email@email.com")
                .enabled(true)
                .experience(Arrays.asList(ProgrammingLanguages.JAVA))
                .firstName("fname")
                .lastName("lname")
                .projects(null)
                .userRole(Role.ADMIN)
                .build();

        user2 = User.builder()
                .username("second")
                .password("127EDFR12EE15631D76B2368926D4F4B")
                .email("sec@email.com")
                .enabled(true)
                .experience(Arrays.asList(ProgrammingLanguages.JAVA))
                .firstName("fsname")
                .lastName("lsname")
                .projects(null)
                .userRole(Role.USER)
                .build();

    }

    @Test
    public void shouldReturnProperUserByUsername(){

        //given
        String username = user1.getUsername();
        Optional<User> user = Optional.of(UserServiceTest.user1);

        when(userRepository.findUserByUsername(username)).thenReturn(user);

        //when
        User userDB = userService.getUserByUsername(username);

        //then
        Assertions.assertThat(userDB.getUsername()).isEqualTo(username);
    }
}
