package com.matuszak.engineer.auth.model.dto;

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
