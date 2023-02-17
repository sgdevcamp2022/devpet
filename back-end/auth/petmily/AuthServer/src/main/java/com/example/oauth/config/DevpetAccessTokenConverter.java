package com.example.oauth.config;

import com.example.oauth.service.UserDetailService;
import com.example.oauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DevpetAccessTokenConverter extends DefaultAccessTokenConverter {

    @Autowired
    UserDetailService userService;

    @Autowired
    DevpetUserAuthenticationConverter userTokenConverter;
    @Override
    public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication)
    {
        super.setUserTokenConverter(userTokenConverter);
        Map<String,?> result = super.convertAccessToken(token,authentication);
        return result;
    }
    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> claims) {
        super.setUserTokenConverter(userTokenConverter);
        OAuth2Authentication authentication = super.extractAuthentication(claims);
        return authentication;
    }

    @Bean
    public UserAuthenticationConverter getUserTokenConverter() {
        return new DevpetUserAuthenticationConverter(userService);
    }

}
