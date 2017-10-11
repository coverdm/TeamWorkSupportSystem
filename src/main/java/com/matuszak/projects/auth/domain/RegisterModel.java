package com.matuszak.projects.auth.domain;

import com.matuszak.projects.auth.annotations.ValidEmail;
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

    @NotEmpty private String username;
    @NotEmpty private String password;
    @NotEmpty private String rePassword;
}
