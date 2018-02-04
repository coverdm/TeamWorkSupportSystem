package com.matuszak.engineer.profile.service;

import com.matuszak.engineer.profile.exception.ProfileAlreadyExists;
import com.matuszak.engineer.profile.exception.ProfileNotFoundException;
import com.matuszak.engineer.profile.model.ProfileId;
import com.matuszak.engineer.profile.model.dto.MinProfileDto;
import com.matuszak.engineer.profile.model.dto.ProfileDto;
import com.matuszak.engineer.profile.model.entity.Profile;
import com.matuszak.engineer.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ModelMapper modelMapper;

    public ProfileDto getProfile(ProfileId profileId) throws ProfileNotFoundException {
        return this.profileRepository.getProfileByProfileId(profileId)
                .map( e-> this.modelMapper.map(e, ProfileDto.class))
                .orElseThrow(ProfileNotFoundException::new);
    }

    public ProfileDto createProfile(ProfileId profileId, ProfileDto profileDto) throws ProfileAlreadyExists {

        Profile profile = Profile.builder()
                .profileId(profileId)
                .avatar(profileDto.getAvatar())
                .contact(profileDto.getContact())
                .name(profileDto.getName())
                .skills(profileDto.getSkills())
                .prefferedRoles(profileDto.getPrefferedRoles())
                .build();

        log.info(profile.toString());
        log.info(profileDto.toString());

        return modelMapper.map(this.profileRepository.save(profile), ProfileDto.class);
    }

    public ProfileDto updateProfile(ProfileId profileId, ProfileDto profileDto) throws ProfileNotFoundException{

        Optional<Profile> profile = this.profileRepository.getProfileByProfileId(profileId);

        profile.ifPresent(userProfile -> {
            userProfile.setAvatar(profileDto.getAvatar());
            userProfile.setContact(profileDto.getContact());
            userProfile.setSkills(profileDto.getSkills());
            userProfile.setName(profileDto.getName());
            userProfile.setPrefferedRoles(profileDto.getPrefferedRoles());
            this.profileRepository.save(userProfile);
        });

        return modelMapper.map(profile.orElseThrow(ProfileNotFoundException::new), ProfileDto.class);
    }

    public void delete(ProfileId profileId) {
        this.profileRepository.getProfileByProfileId(profileId).ifPresent(profileRepository::delete);
    }

    public Collection<MinProfileDto> getMinProfiles(Collection<ProfileId> profilesId){

        Collection<MinProfileDto> collect = new ArrayList<>();

        for(ProfileId profileId: profilesId){
            log.info(profileId.toString());
            collect.add(getMinProfile(profileId));
        }

        log.info(collect.toString());

        return collect;
    }

    public MinProfileDto getMinProfile(ProfileId profileId) {

        log.info(profileId.toString());

        return this.profileRepository.getProfileByProfileId(profileId)
                .map(e -> modelMapper.map(e, MinProfileDto.class))
                .orElseThrow(ProfileNotFoundException::new);
    }
}
