package com.matuszak.engineer.auth.model.entity;

import com.matuszak.engineer.auth.model.SubjectId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document
public class Subject{

    private SubjectId subjectId;
    private String password;
    private Boolean enabled;

    private List<SimpleGrantedAuthority> authorities;

    public String getSubjectId(){
        return subjectId.getEmail();
    }
}