package com.matuszak.engineer.domain.auth.model.entity;

import com.matuszak.engineer.domain.auth.model.SecurityLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@Builder
@Entity
@Data
public class Subject {

    @EmbeddedId
    private SubjectId subjectId;

    private String email;
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private SecurityLevel securityLevel;

    private Boolean enabled;

    @ElementCollection
    private List<SimpleGrantedAuthority> authorities;

    public String getSubjectId() {
        return subjectId.getId();
    }

    private Subject() { // just for hibernate
    }
}
