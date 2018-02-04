package com.matuszak.engineer.profile.repository;

import com.matuszak.engineer.profile.model.ProfileId;
import com.matuszak.engineer.profile.model.entity.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.Optional;

public interface ProfileRepository extends MongoRepository<Profile, ProfileId> {
    Optional<Profile> getProfileByProfileId(ProfileId profileId);
    Optional<Collection<Profile>> getProfilesByProfileIdIn(Collection<ProfileId> profiles);
}
