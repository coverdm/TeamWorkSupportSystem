package com.matuszak.engineer.domain.profile.model.repository;

import com.matuszak.engineer.domain.profile.model.ProfileId;
import com.matuszak.engineer.domain.profile.model.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, ProfileId> {
}
