package com.matuszak.engineer.profile.model.dto;

import com.matuszak.engineer.profile.model.*;
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

    private ProfileId profileId;
    private Name name;
    private Avatar avatar;

    private Collection<Skill> skills;
    private Collection<PrefferedRole> prefferedRoles;

    private Contact contact;
}
