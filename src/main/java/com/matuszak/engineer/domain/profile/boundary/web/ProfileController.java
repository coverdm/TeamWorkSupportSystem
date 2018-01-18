package com.matuszak.engineer.domain.profile.boundary.web;

import com.matuszak.engineer.domain.profile.exception.ProfileAlreadyExists;
import com.matuszak.engineer.domain.profile.exception.ProfileNotFoundException;
import com.matuszak.engineer.domain.profile.model.ProfileId;
import com.matuszak.engineer.domain.profile.model.dto.MinProfileDto;
import com.matuszak.engineer.domain.profile.model.dto.ProfileDto;
import com.matuszak.engineer.domain.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Log
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity getProfile(@RequestParam String profileId){

        try{

            ProfileDto profile = this.profileService.getProfile(new ProfileId(profileId));

            return new ResponseEntity<>(profile, HttpStatus.OK);
        }catch (ProfileNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/minWorkers")
    public ResponseEntity<Collection<MinProfileDto>> getMinProfiles(@RequestBody Collection<String> profilesIds){

        List<ProfileId> profileIds = profilesIds
                .stream()
                .map(ProfileId::new)
                .collect(Collectors.toList());
        try {
            return new ResponseEntity<>(this.profileService.getMinProfiles(profileIds), HttpStatus.OK);
        }catch (ProfileNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/create")
    public ResponseEntity<ProfileDto> createProfile(@RequestParam String profileId, @RequestBody ProfileDto profileDto){

        try{
            ProfileDto profile = this.profileService.createProfile(new ProfileId(profileId), profileDto);
            return new ResponseEntity<>(profile, HttpStatus.CREATED);
        }catch (ProfileAlreadyExists e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

    }

    @PutMapping("/update")
    public ResponseEntity<ProfileDto> updateProfile(@RequestParam String profileId, @RequestBody ProfileDto profileDto){

        try{
            ProfileDto profile = this.profileService.updateProfile(new ProfileId(profileId), profileDto);
            return new ResponseEntity<>(profile, HttpStatus.OK);
        }catch (ProfileNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteProfile(@RequestParam String profileId){

        try{
            this.profileService.delete(new ProfileId(profileId));
            return new ResponseEntity(HttpStatus.OK);
        }catch (ProfileNotFoundException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }
}
