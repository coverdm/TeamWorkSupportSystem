package com.matuszak.engineer.domain.profile.repository;

import com.matuszak.engineer.domain.profile.model.entity.Profile;
import com.matuszak.engineer.domain.profile.model.ProfileId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, ProfileId> {
    Optional<Profile> getProfileByProfileId(ProfileId profileId);
}
