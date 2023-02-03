package com.example.shoh_oauth.controller;

import com.example.shoh_oauth.data.dto.SignUpRequest;
import com.example.shoh_oauth.exception.ValidationException;
import com.example.shoh_oauth.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/oauth/sign-up")
    public ResponseEntity<?> signUpNewUser(
                            @RequestParam String username,
                            @RequestParam String name,
                            @RequestParam String nickname,
                            @RequestParam String password,
                            @RequestParam String age,
                            @RequestParam String gender,
                            @RequestParam String phone,
                            @RequestParam String provider){

        //userService.checkDuplicateNickname(nickname);

        SignUpRequest signUpRequest = SignUpRequest.builder()
                .username(username)
                .name(name)
                .nickname(nickname)
                .password(password)
                .age(age)
                .gender(gender)
                .phone(phone)
                .provider(provider)
                .build();

        if (provider != "") {
            userService.saveKaKaoUserLast(signUpRequest);
            return ResponseEntity.ok("카카오 회원가입");
        }

        //userService.checkDuplicateEmail(username);
        userService.saveUser(signUpRequest);



        return ResponseEntity.ok("일반 회원가입");
    }

    // 카카오 프로필 정보(자동 회원 가입)
    @PostMapping(value = "/oauth/kakao")
    public ResponseEntity<?> saveKaKaoUser(@RequestParam String username,
                                           @RequestParam String name,
                                           //@RequestParam String nickname,
                                           @RequestParam String password
                                           //@RequestParam String age,
                                           //@RequestParam String gender,
                                           //@RequestParam String phone
                                           ){


        userService.checkDuplicateKaKaoEmail(username);

        SignUpRequest signUpRequest = SignUpRequest.builder()
                .username(username)
                .name(name)
                .password(password)
                .build();

        userService.saveKaKaoUser(signUpRequest);

        return ResponseEntity.ok("카카오 자동 회원 가입 성공(1차)");
    }

    @GetMapping(value = "/api/token")
    public ResponseEntity<?> apiTest()  {

        return ResponseEntity.ok("api test good");
    }

    @GetMapping(value = "/users/user")
    public ResponseEntity<?> user()  {

        return ResponseEntity.ok("user test good");
    }
}