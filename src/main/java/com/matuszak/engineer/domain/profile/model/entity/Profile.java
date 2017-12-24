package com.matuszak.engineer.domain.profile.model.entity;

import com.matuszak.engineer.domain.profile.Contact;
import com.matuszak.engineer.domain.profile.ProfileId;
import com.matuszak.engineer.domain.profile.Skill;
import com.matuszak.engineer.domain.project.model.Avatar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @EmbeddedId
    private ProfileId profileId;

    private String firstName;
    private String lastName;
    private Avatar avatar;

    @ElementCollection
    private Collection<Skill> skills;

    private Contact contact;

    public void addSkill(Skill skill){
        this.skills.add(skill);
    }
}
