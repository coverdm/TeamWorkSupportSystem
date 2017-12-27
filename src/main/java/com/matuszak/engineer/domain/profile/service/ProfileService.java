package com.matuszak.engineer.domain.profile.service;

import com.matuszak.engineer.domain.profile.model.ProfileId;
import com.matuszak.engineer.domain.profile.exception.ProfileAlreadyExists;
import com.matuszak.engineer.domain.profile.exception.ProfileNotFoundException;
import com.matuszak.engineer.domain.profile.model.dto.ProfileDto;
import com.matuszak.engineer.domain.profile.model.entity.Profile;
import com.matuszak.engineer.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ModelMapper modelMapper;

    public ProfileDto getProfile(ProfileId profileId) throws ProfileNotFoundException {
        return this.profileRepository.getProfileByProfileId(profileId)
                .map( e-> this.modelMapper.map(e, ProfileDto.class))
                .orElseThrow(ProfileNotFoundException::new);
    }

    public ProfileDto createProfile(ProfileId profileId, ProfileDto profileDto) throws ProfileAlreadyExists {

        Profile profile = new Profile().builder()
                .profileId(profileId)
                .avatar(profileDto.getAvatar())
                .contact(profileDto.getContact())
                .name(profileDto.getName())
                .skills(profileDto.getSkills())
                .build();

        return modelMapper.map(this.profileRepository.save(profile), ProfileDto.class);
    }

    public ProfileDto updateProfile(ProfileId profileId, ProfileDto profileDto) throws ProfileNotFoundException{

        Optional<Profile> profile = this.profileRepository.getProfileByProfileId(profileId);

        profile.ifPresent(userProfile -> {
            userProfile.setAvatar(profileDto.getAvatar());
            userProfile.setContact(profileDto.getContact());
            userProfile.setSkills(profileDto.getSkills());
            userProfile.setName(profileDto.getName());
            this.profileRepository.save(userProfile);
        });

        return modelMapper.map(profile.orElseThrow(ProfileNotFoundException::new), ProfileDto.class);
    }

    public void delete(ProfileId profileId) {
        this.profileRepository.getProfileByProfileId(profileId).ifPresent(profileRepository::delete);
    }
}
