package com.matuszak.engineer.domain.auth.model.entity;

import com.matuszak.engineer.domain.auth.model.SecurityLevel;
import com.matuszak.engineer.infrastructure.entity.User;
import com.matuszak.engineer.infrastructure.entity.UserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Builder
public class Subject extends User{

    private String email;
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private SecurityLevel securityLevel;

    private Boolean enabled;

    @ElementCollection
    private List<SimpleGrantedAuthority> authorities;

    public Subject(String email, String username, String password, SecurityLevel securityLevel, Boolean enabled, List<SimpleGrantedAuthority> authorities) {

        super.userId = new UserId(UserId.generate());

        this.email = email;
        this.username = username;
        this.password = password;
        this.securityLevel = securityLevel;
        this.enabled = enabled;
        this.authorities = authorities;
    }
}
