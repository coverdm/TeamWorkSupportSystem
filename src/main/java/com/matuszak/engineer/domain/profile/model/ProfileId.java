package com.matuszak.engineer.domain.profile.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class ProfileId implements Serializable {
    private String profileId;
}
