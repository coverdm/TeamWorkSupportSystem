package com.matuszak.engineer.domain.profile.repository;

import com.matuszak.engineer.domain.profile.model.ProfileId;
import com.matuszak.engineer.domain.profile.model.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public interface ProfileRepository extends JpaRepository<Profile, ProfileId> {
    Optional<Profile> getProfileByProfileId(ProfileId profileId);
    Optional<Collection<Profile>> getProfilesByProfileIdIn(Collection<ProfileId> profiles);
}
