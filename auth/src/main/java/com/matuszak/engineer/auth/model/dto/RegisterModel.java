package com.matuszak.engineer.auth.model.dto;

import com.matuszak.engineer.auth.annotations.ValidEmail;
import com.matuszak.engineer.auth.annotations.ValidPassword;
import lombok.*;

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
