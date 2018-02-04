package com.matuszak.engineer.profile.model.dto;

import com.matuszak.engineer.profile.model.Avatar;
import com.matuszak.engineer.profile.model.Name;
import com.matuszak.engineer.profile.model.ProfileId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MinProfileDto {
    private ProfileId profileId;
    private Name name;
    private Avatar avatar;
}
