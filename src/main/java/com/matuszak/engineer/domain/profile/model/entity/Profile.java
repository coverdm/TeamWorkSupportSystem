package com.matuszak.engineer.domain.profile.model.entity;

import com.matuszak.engineer.domain.profile.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Collection;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @EmbeddedId
    private ProfileId profileId;

    private Name name;
    private Avatar avatar;

    @ElementCollection
    private Collection<Skill> skills;

    @ElementCollection
    private Collection<PrefferedRole> prefferedRoles;

    private Contact contact;

    public Profile(ProfileId profileId){
        this.profileId = profileId;
    }


    public void addSkill(Skill skill){
        this.skills.add(skill);
    }

    public void addRole(PrefferedRole prefferedRole){
        this.prefferedRoles.add(prefferedRole);
    }
}
