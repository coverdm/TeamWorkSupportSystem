package com.matuszak.engineer.domain.auth.model.entity;

import com.matuszak.engineer.domain.auth.model.SubjectId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Subject{

    @EmbeddedId
    private SubjectId subjectId;
    private String password;
    private Boolean enabled;

    @ElementCollection
    private List<SimpleGrantedAuthority> authorities;

    public String getSubjectId(){
        return subjectId.getEmail();
    }
}