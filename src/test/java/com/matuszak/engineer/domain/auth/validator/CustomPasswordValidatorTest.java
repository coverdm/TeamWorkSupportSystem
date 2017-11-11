package com.matuszak.engineer.domain.auth.validator;

import com.matuszak.engineer.Application;
import com.matuszak.engineer.domain.auth.exceptions.IllegalPasswordFormatException;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest
public class CustomPasswordValidatorTest {

    CustomPasswordValidator customPasswordValidator = new CustomPasswordValidator();

    @Test
    public void shouldReturnTrueWhenPasswordIsCorrect(){

        //given
        String rawPassword = "SomePassword123";

        //when
        boolean result = customPasswordValidator.validateRawPassword(rawPassword);

        //then
        assertThat(result).isTrue();
    }

    @Test
    public void shoudlThrowExceptionWhenPasswordHasNotNumbers(){

        //given
        String rawPassword = "SomePassword";

        //when && then
        assertThatExceptionOfType(IllegalPasswordFormatException.class)
                .isThrownBy(() -> customPasswordValidator.validateRawPassword(rawPassword));
    }
    @Test
    public void shouldThrowExceptionWhenPasswordHasNotUpperCases(){

        //given
        String rawPassword = "somepassword123";

        //when && then
        assertThatExceptionOfType(IllegalPasswordFormatException.class)
                .isThrownBy(() -> customPasswordValidator.validateRawPassword(rawPassword));    }

}