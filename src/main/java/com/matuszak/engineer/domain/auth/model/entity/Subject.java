package com.matuszak.engineer.domain.auth.model.entity;

import com.matuszak.engineer.domain.auth.model.SecurityLevel;
import com.matuszak.engineer.domain.auth.model.SubjectId;
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
@AllArgsConstructor
@Data
@Builder
public class Subject{

    @EmbeddedId
    private SubjectId subjectId;
    private String username;
    private String password;
    private Boolean enabled;

    @Enumerated(EnumType.STRING)
    private SecurityLevel securityLevel;

    @ElementCollection
    private List<SimpleGrantedAuthority> authorities;

    public String getSubjectId(){
        return subjectId.getEmail();
    }
}