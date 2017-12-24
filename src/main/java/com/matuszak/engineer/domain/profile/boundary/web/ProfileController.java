package com.matuszak.engineer.domain.profile.boundary.web;

import com.matuszak.engineer.domain.profile.ProfileId;
import com.matuszak.engineer.domain.profile.model.dto.ProfileDto;
import com.matuszak.engineer.domain.profile.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private ProfileService profileService;

    @GetMapping("/")
    public ResponseEntity<ProfileDto> getProfile(@RequestParam String profileId){
        ProfileDto profile = this.profileService.getProfile(new ProfileId(profileId));
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

}
