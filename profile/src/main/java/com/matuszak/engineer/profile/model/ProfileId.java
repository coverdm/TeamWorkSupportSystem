package com.matuszak.engineer.profile.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class ProfileId implements Serializable {
    private String profileId;
}
