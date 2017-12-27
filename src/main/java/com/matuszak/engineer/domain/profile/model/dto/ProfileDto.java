package com.matuszak.engineer.domain.profile.model.dto;

import com.matuszak.engineer.domain.profile.model.Contact;
import com.matuszak.engineer.domain.profile.model.PrefferedRole;
import com.matuszak.engineer.domain.profile.model.Skill;
import com.matuszak.engineer.domain.profile.model.Name;
import com.matuszak.engineer.domain.project.model.Avatar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {

    private Name name;
    private Avatar avatar;

    private Collection<Skill> skills;
    private Collection<PrefferedRole> prefferedRoles;

    private Contact contact;
}
