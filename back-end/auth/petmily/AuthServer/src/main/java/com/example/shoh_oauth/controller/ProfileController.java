package com.example.shoh_oauth.controller;

import com.example.shoh_oauth.data.dto.PasswordDto;
import com.example.shoh_oauth.data.dto.ProfileDto;
import com.example.shoh_oauth.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{username}")
    public ResponseEntity<ProfileDto> getProfile(@PathVariable("username") String username) {

        ProfileDto profileDto = profileService.getProfile(username);

        return ResponseEntity.ok(profileDto);
    }

    @PutMapping("/{username}")
    public ResponseEntity<String> putProfile(@PathVariable("username") String username,
                                             @RequestBody ProfileDto profileDto) {

        profileService.putProfile(username, profileDto);

        return ResponseEntity.ok("프로필 수정 성공");
    }

    @PutMapping("/password/{username}")
    public ResponseEntity<String> putPassword(@PathVariable("username") String username,
                                              @RequestBody PasswordDto passwordDto) {

        profileService.putPassword(username, passwordDto);

        return ResponseEntity.ok("비밀번호 수정 성공");
    }


}