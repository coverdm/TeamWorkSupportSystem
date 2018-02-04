package com.matuszak.engineer.auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
