package com.matuszak.engineer.domain.profile.model.dto;

import com.matuszak.engineer.domain.profile.model.Avatar;
import com.matuszak.engineer.domain.project.model.dto.Name;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MinProfileDto {
    private Name name;
    private Avatar avatar;
}
