package com.matuszak.engineer.domain.auth.model.entity;

import com.matuszak.engineer.domain.auth.model.SecurityLevel;
import com.matuszak.engineer.infrastructure.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Entity
@AllArgsConstructor
@Data
@Builder
public class Subject extends BaseEntity{

    private String email;
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private SecurityLevel securityLevel;

    private Boolean enabled;

    @ElementCollection
    private List<SimpleGrantedAuthority> authorities;
}
