package com.example.shoh_oauth.service;

import com.example.shoh_oauth.data.dto.SignUpRequest;

public interface UserService {

    void saveUser(SignUpRequest signUpRequest);
    void saveKakaoUser(String email, String nickname);
//
//    void updateProfile(String username, UpdateProfileRequest updateProfileRequest);
//
//    UserDetails loginOAuth2User(String provider, OAuth2Token oAuth2Token, OAuth2UserInfo userInfo);
//
//    Optional<OAuth2AccountDTO> getOAuth2Account(String username);
//
//    UserDetails linkOAuth2Account(String username, String provider, OAuth2Token oAuth2Token, OAuth2UserInfo userInfo);
//
//    OAuth2AccountDTO unlinkOAuth2Account(String username);
//
//    Optional<OAuth2AccountDTO> withdrawUser(String username);

}
