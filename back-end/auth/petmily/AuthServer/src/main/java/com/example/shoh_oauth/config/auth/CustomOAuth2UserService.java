package com.example.shoh_oauth.config.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //(1번) DefaultOAuth2User 서비스를 통해 User 정보를 가져와야 하기 때문에 대리자 생성
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();

        //	2번
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        //(3번) 네이버 로그인인지 구글로그인인지 서비스를 구분해주는 코드
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        //OAuth2 로그인 진행시 키가 되는 필드값 프라이머리키와 같은 값 네이버 카카오 지원 x
        String userNameAttributeName = userRequest.getClientRegistration() // -> Oauth 서버에서 식별
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        log.info("registrationId = {}", registrationId);
        log.info("userNameAttributeName = {}", userNameAttributeName);
        log.info("oAuth2User.getAttributes() = {}", oAuth2User.getAttributes());

        String password = String.valueOf(oAuth2User.getAttributes().get("id"));
        log.info("id : " + password);

        // 4번 OAuth2UserService 를 통해 가져온 Oauth 서버에 있는 회원 데이터를 담을 클래스
        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(registrationId, password, oAuth2User.getAttributes());

        // 여기서 DB 에 로그인한 유저 정보가 들어 있는 oAuth2Attribute 을 저장?
        Map<String, Object> memberAttribute = oAuth2Attribute.convertToMap();

        // 강제 형변환 하면 안됨
        // Principal principal = (Principal) oAuth2User;
        // 로그인한 유저를 리턴함 인증된 유저 즉 인증된 Authentication 객체라고 생각해도 무방?
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), memberAttribute, "id");
    }

}
