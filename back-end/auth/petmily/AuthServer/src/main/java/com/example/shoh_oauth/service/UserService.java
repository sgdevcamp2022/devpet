package com.example.shoh_oauth.service;

import com.example.shoh_oauth.data.dto.SignUpRequest;

public interface UserService {

    void saveUser(SignUpRequest signUpRequest);

//    void updateProfile(String username, UpdateProfileRequest updateProfileRequest);

//    Optional<OAuth2AccountDTO> getOAuth2Account(String username);


}
