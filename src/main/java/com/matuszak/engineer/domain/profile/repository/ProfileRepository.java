package com.matuszak.engineer.domain.profile.repository;

import com.matuszak.engineer.domain.profile.model.entity.Profile;
import com.matuszak.engineer.domain.profile.ProfileId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, ProfileId> {
}
