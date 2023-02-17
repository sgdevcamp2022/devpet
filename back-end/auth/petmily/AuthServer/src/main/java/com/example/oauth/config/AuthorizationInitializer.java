package com.example.oauth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// 토큰 발급, 관리 해주는곳
// oauth2 인증을 거치는 과정을 AuthorizationInitializer 가 처리한다
//
@Slf4j
@Configuration
@EnableAuthorizationServer
public class AuthorizationInitializer extends AuthorizationServerConfigurerAdapter {

    @Value("${security.oauth2.clientID}")
    private String clientID;
    @Value("${security.oauth2.clientSecret}")
    private String clientSecret;
    @Value("${security.oauth2.signkey}")
    private String signKey;
    // 얘가 실제로 인증을 한다, 인증 토큰을 관리하는 객체
    @Autowired
    private AuthenticationManager authenticationManager; // grant_type password 를 사용하려면 필수
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserDetailsService service;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private DevpetAccessTokenConverter devpetAccessTokenConverter;
//    @Autowired
//    private CustomAccessTokenConverter customAccessTokenConverter;

    /*
    * AuthorizationInitializer 에서 발급하는 oauth 토큰들을 저장하는 저장소이다
    * token store로 JWTTokenStore를 사용하겠다
      JWT 디코딩 하기 위한 설정 */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {

        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setAccessTokenConverter(devpetAccessTokenConverter);
        converter.setSigningKey(signKey);
        //converter.setAccessTokenConverter(customAccessTokenConverter);
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {

        /* token store로 JWTTokenStore를 사용하겠다 */
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    // Oauth2 인증서버 자체의 보안 정보를 설정하는 부분
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.passwordEncoder(passwordEncoder)
                .checkTokenAccess("isAuthenticated()"); // /oauth/check_token에 대한 허용을 의미하며 access token에 포함된 데이터를 복호화하여 출력
                //.tokenKeyAccess("permitAll()") // /oauth/token_key 에 대한 허용을 의미하며 jwt 토큰 검증을 위한 공개키 노출
                //.addTokenEndpointAuthenticationFilter(CorsFilter(corsConfigurationSource())) // cors 정책을 지원하기 위해 추가. 현재는 모든 요청을 허용하도록 되어있음.
    }

    /* 클라이언트 대한 정보를 설정하는 부분 */
    @Override
    public void configure(ClientDetailsServiceConfigurer client) throws Exception {

        /* jdbc(DataBase)를 이용하는 방식 */
        client.jdbc(dataSource).passwordEncoder(passwordEncoder);
    }

    // OAuth2 서버가 작동하기 위한 Endpoint 에 대한 정보를 설정
    // 인가 방법과 토큰 엔드포인트 설정 및 토큰 서비스를 정의합니다.
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoint) throws Exception {

        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter()));

        endpoint
                .authenticationManager(authenticationManager) // authenticationManager - password 값으로 사용자를 인증하고 인가
                .tokenStore(tokenStore()) // tokenStore - token이 저장될 기본 store를 정의
                .userDetailsService(service) // userDetailsService - 사용자를 인증하고 인가하는 서비스를 설정
                .accessTokenConverter(jwtAccessTokenConverter()) // accessTokenConverter - access token을 jwt 토큰으로 변환하기 위해 사용하며 jwtSecret 키를 통해 jwt 토큰을 설정
                .exceptionTranslator(authorizationWebResponseExceptionTranslator())
                .tokenEnhancer(tokenEnhancerChain);
                // .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE) allowedTokenEndpointRequestMethods - token endpoint를 사용할 때 허용할 method들을 설정
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }

    public WebResponseExceptionTranslator authorizationWebResponseExceptionTranslator() {
        return new DefaultWebResponseExceptionTranslator() {

            @Override
            public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
                Map responseMap = new HashMap();
                String refreshCheck = e.getMessage();
                if (refreshCheck.substring(0, 3).equals("Bad")) {
                    responseMap.put("message", "4003");
                } else if (refreshCheck.substring(8, 15).equals("refresh")) {
                    // refreshToken
                    responseMap.put("message", "4010");
                } else if (refreshCheck.substring(0,6).equals("Cannot")) {
                    // refreshToken
                    responseMap.put("message", "4010");
                } else if (refreshCheck.substring(10, 17).equals("expired")) {
                    responseMap.put("message", "4004");
                } else {
                    // id, pw 잘못 입력
                    responseMap.put("message", "4003");
                }

                log.info(e.getMessage());
                return new ResponseEntity(responseMap, HttpStatus.UNAUTHORIZED);
            }
        };
    }
}
