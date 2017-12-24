package com.matuszak.engineer.domain.profile.service;

import com.matuszak.engineer.domain.profile.ProfileId;
import com.matuszak.engineer.domain.profile.model.dto.ProfileDto;
import com.matuszak.engineer.domain.profile.model.entity.Profile;
import com.matuszak.engineer.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ModelMapper modelMapper;

    public ProfileDto getProfile(ProfileId profileId) {
        return this.modelMapper.map(this.profileRepository.findOne(profileId), ProfileDto.class);
    }
}
