package com.example.shoh_oauth.controller;

import com.example.shoh_oauth.data.dto.SignUpRequest;
import com.example.shoh_oauth.exception.ValidationException;
import com.example.shoh_oauth.repository.VUserRepository;
import com.example.shoh_oauth.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private TokenEndpoint tokenEndpoint;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private VUserRepository userRepository;

    @PostMapping("/oauth/sign-up")
    public ResponseEntity<?> signUpNewUser(@RequestBody @Valid SignUpRequest signUpRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) throw new ValidationException("회원가입 유효성 검사 실패.", bindingResult.getFieldErrors());
        userService.saveUser(signUpRequest);
        return ResponseEntity.ok("Success");
    }

//    @PostMapping(value = "/oauth/token")
//    public ResponseEntity<OAuth2AccessToken> postAccessToken(@RequestParam Map<String, String> parameters, Principal principal) throws HttpRequestMethodNotSupportedException {
//
//        if (!String.valueOf(parameters.get("grant_type")).equals("refresh_token")) {
//            userService.checkPassword(parameters);
//            userService.checkLoginEmail(parameters);
//        }
//        //OAuth2RefreshToken
//        return tokenEndpoint.postAccessToken(principal, parameters);
//    }
//
    @GetMapping(value = "/api/token")
    public ResponseEntity<?> apiTest()  {

        return ResponseEntity.ok("api test good");
    }

    @GetMapping(value = "/users/user")
    public ResponseEntity<?> user()  {

        return ResponseEntity.ok("user test good");
    }
}