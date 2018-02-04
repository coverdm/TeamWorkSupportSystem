package com.matuszak.engineer.profile.model.entity;

import com.matuszak.engineer.profile.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Data
@Builder
@Document
public class Profile {

    @Id
    private ProfileId profileId;

    private Name name;
    private Avatar avatar;

    private Collection<Skill> skills;

    private Collection<PrefferedRole> prefferedRoles;

    private Contact contact;

    public void addSkill(Skill skill){
        this.skills.add(skill);
    }

    public void addRole(PrefferedRole prefferedRole){
        this.prefferedRoles.add(prefferedRole);
    }
}