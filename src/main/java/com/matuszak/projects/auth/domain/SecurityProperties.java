package com.matuszak.projects.auth.domain;

import lombok.*;
import lombok.extern.java.Log;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

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
