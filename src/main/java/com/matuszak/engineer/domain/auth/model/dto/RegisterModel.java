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

//    @ValidEmail
    private String email;

    @NotEmpty
    private String username;

    @NotEmpty
//    @ValidPassword
    private String password;

    @NotEmpty
//    @ValidPassword
    private String rePassword;
}
