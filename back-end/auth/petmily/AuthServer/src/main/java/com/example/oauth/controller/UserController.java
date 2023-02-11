package com.example.oauth.controller;

import com.example.oauth.data.dto.SignUpRequest;
import com.example.oauth.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUpNewUser(
                            @RequestParam String username,
                            @RequestParam String name,
                            @RequestParam String password,
                            @RequestParam String phone,
                            @RequestParam String provider){

        SignUpRequest signUpRequest = SignUpRequest.builder()
                .username(username)
                .name(name)
                .password(password)
                .phone(phone)
                .provider(provider)
                .build();

        if (!Objects.equals(provider, "")) {
            userService.saveKaKaoUserLast(signUpRequest);
            return ResponseEntity.ok("카카오 회원가입");
        }

        //userService.checkDuplicateEmail(username);
        userService.saveUser(signUpRequest);


        return ResponseEntity.ok("일반 회원가입");
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<?> changePassword(@PathVariable("userId") Long userId, @RequestParam String password)
    {
        userService.changePassword(userId,password);

        return ResponseEntity.ok("패스워드 변경 완료");
    }

    // 카카오 프로필 정보(자동 회원 가입)
//    @PostMapping(value = "/kakao")
//    public ResponseEntity<?> saveKaKaoUser(@RequestParam String username,
//                                           @RequestParam String name,
//                                           @RequestParam String nickname,
//                                           @RequestParam String password,
//                                           @RequestParam String age,
//                                           @RequestParam String gender,
//                                           @RequestParam String phone,
//                                           @RequestParam String provider){
//
//        userService.checkDuplicateKaKaoEmail(username);
//
//        SignUpRequest signUpRequest = SignUpRequest.builder()
//                .username(username)
//                .name(name)
//                .password(password)
//                .build();
//
//        userService.saveKaKaoUser(signUpRequest);
//
//        ProfileDto profileDto = ProfileDto.builder()
//                .name(signUpRequest.getName())
//                .username(signUpRequest.getUsername())
//                .build();
//
//        profileService.saveProfile(profileDto);
//
//        return ResponseEntity.ok("카카오 자동 회원 가입 성공(1차)");
//    }
}