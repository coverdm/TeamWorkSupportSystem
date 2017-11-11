package com.matuszak.engineer.domain.auth.validator;

import com.matuszak.engineer.Application;
import com.matuszak.engineer.domain.auth.exceptions.IllegalEmailFormatException;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
public class CustomEmailValidatorTest {

    CustomEmailValidator customEmailValidator = new CustomEmailValidator();

    @Test
    public void shouldReturnTrueWhenEmailIsValid(){

        //given
        String firstEmail = "GoodEmail@gmail.com";
        String secondEmail = "Good.Email@Gmail.Com";
        String thirdEmail = "G.O.O.D.E.M.A.I.L@gm.ail.COM";

        //when
        boolean firstResult = customEmailValidator.isValid(firstEmail, null);
        boolean secondResult = customEmailValidator.isValid(secondEmail, null);
        boolean thirdResult = customEmailValidator.isValid(thirdEmail, null);

        //then
        assertThat(firstResult).isTrue();
        assertThat(secondResult).isTrue();
        assertThat(thirdResult).isTrue();
    }

    @Test
    public void shouldThrowIllegalEmailFormatExceptionWhenEmailHasNotAtSign(){

        //given
        String email = "InvalidMail";

        //when && then
        assertThatExceptionOfType(IllegalEmailFormatException.class)
                .isThrownBy(() -> customEmailValidator.isValid(email, null));
    }

    @Test
    public void shouldThrowIllegalEmailFormatExceptionWhenEmailHasNotDomain(){

        //given
        String email = "Invalid@Mail";

        //when && then
        assertThatExceptionOfType(IllegalEmailFormatException.class)
                .isThrownBy(() -> customEmailValidator.isValid(email, null));
    }
}
