package com.matuszak.engineer.domain.auth.model.dto;

import com.matuszak.engineer.domain.auth.annotations.ValidEmail;
import com.matuszak.engineer.domain.auth.annotations.ValidPassword;
import lombok.*;

@Builder
@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class LoginModel {

//    @ValidEmail
    private String email;

//    @ValidPassword
    private String password;

    private String username;
}
