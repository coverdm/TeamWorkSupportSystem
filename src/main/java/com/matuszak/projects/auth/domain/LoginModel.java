package com.matuszak.projects.auth.domain;

import com.matuszak.projects.auth.annotations.ValidEmail;
import lombok.*;

@Builder
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class LoginModel {

    private String email;
    private String password;

    @Deprecated
    private String username;
}
