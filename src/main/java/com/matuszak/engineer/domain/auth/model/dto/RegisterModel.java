package com.matuszak.engineer.domain.auth.model.dto;

import com.matuszak.engineer.domain.auth.annotations.ValidEmail;
import com.matuszak.engineer.domain.auth.annotations.ValidPassword;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

@Builder
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RegisterModel {

    @ValidEmail
    private String email;

    @ValidPassword
    private String password;

    @ValidPassword
    private String confirmPassword;
}
