package com.matuszak.engineer.domain.auth.model;

import lombok.*;
import lombok.extern.java.Log;

@AllArgsConstructor
@NoArgsConstructor
@Log
@Builder
@Data
public class SecurityProperties {

    private boolean isEnabled;
    private boolean isCredentialsNonExpired;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;

}
