package com.example.shoh_oauth.controller;

import com.example.shoh_oauth.service.UserService;
import com.example.shoh_oauth.service.UserServiceImpl;
import com.example.shoh_oauth.service.api.KakaoAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("/login/oauth2")
public class KakaoController {

    private final KakaoAPI kakaoApi;
    private final UserServiceImpl userServiceImpl;
    private final TokenEndpoint tokenEndpoint;

    private String CLIENT_ID = "d2100d0b226da99419acbecfdfc7628c";
    private String REDIRECT_URL = "http://localhost:8080/login/oauth2/code/kakao";
    private String CLIENT_SECRET = "VYOeVvPJuHLM3Y8Rrv0vh8Ch2quhXIiv";

//    @GetMapping("/code/kakao")
//    public String getCode(@RequestParam("code") String code, Principal principal) {
//        String access_token = kakaoApi.getAccessToken(code);
//        HashMap<String, Object> userInfo = kakaoApi.getUserInfo(access_token);
//        //System.out.println("accessToken" + access_token);
//        System.out.println("login Controller : " + userInfo);
//
//        userServiceImpl.saveKakaoUser((String) userInfo.get("email"), (String) userInfo.get("nickname"));
//
//        //ResponseEntity<OAuth2AccessToken> response = tokenEndpoint.postAccessToken(principal, parameters);
//
////        return tokenEndpoint.postAccessToken(principal, parameters);
//        return "index";
//    }

    @GetMapping("/token")
    public String getToken() {



        return "토큰 : ";
    }



//    @GetMapping("/auth")
//    public ResponseEntity<String> auth(@RequestParam String code) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(generateParam(code), headers);
//
//
//        return requestAuth(kakaoTokenRequest);
//    }

//    private MultiValueMap<String, String> generateParam(String code) {
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("grant_type", "authorization_code");
//        params.add("client_id", CLIENT_ID);
//        params.add("redirect_uri", REDIRECT_URL);
//        params.add("code", code);
//        params.add("client_secret", CLIENT_SECRET);
//        return params;
//    }
//
//
//    private ResponseEntity<String> requestAuth(HttpEntity request) {
//
//        RestTemplate restTemplate = new RestTemplate();
//        return restTemplate.exchange(
//                "https://kauth.kakao.com/oauth/token",
//                HttpMethod.POST,
//                request,
//                String.class
//        );
//    }
//
//    private HttpEntity<MultiValueMap<String, String>> generateProfileRequest(OAuth2Token oAuth2Token) {
//        HttpHeaders headers = new HttpHeaders();
//
//        headers.add("Authorization", "Bearer " + oAuth2Token.getTokenValue());
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//        return new HttpEntity<>(headers);
//
//    }
//
//    private ResponseEntity<String> requestProfile(HttpEntity request) {
//        RestTemplate restTemplate = new RestTemplate();
//        return restTemplate.exchange("https://kapi.kakao.com/v2/user/me",
//                HttpMethod.POST,
//                request,
//                String.class
//        );
//    }

}
