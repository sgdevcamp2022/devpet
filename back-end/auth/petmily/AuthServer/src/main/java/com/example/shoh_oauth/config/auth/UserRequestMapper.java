package com.example.shoh_oauth.config.auth;

import com.example.shoh_oauth.data.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class UserRequestMapper {

    public UserDto getUser(OAuth2User oAuth2User) {

        Map<String, Object> attributes = oAuth2User.getAttributes();
        log.info(String.valueOf(attributes.get("username")));
        log.info(String.valueOf(attributes.get("name")));

        return UserDto.builder()
                .username((String) attributes.get("username"))
                .name((String) attributes.get("name"))
                .password(String.valueOf(attributes.get("id")))
                .build();
    }

}