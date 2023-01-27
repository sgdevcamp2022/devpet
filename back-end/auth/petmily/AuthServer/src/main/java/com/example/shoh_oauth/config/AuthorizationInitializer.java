package com.example.shoh_oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;

// 토큰 발급, 관리 해주는곳
// oauth2 인증을 거치는 과정을 AuthorizationInitializer 가 처리한다
//
@Configuration
@EnableAuthorizationServer
public class AuthorizationInitializer extends AuthorizationServerConfigurerAdapter {

    private final String clientID = "dev";
    private final String clientSecret = "pet";
    // 얘가 실제로 인증을 한다, 인증 토큰을 관리하는 객체
    @Autowired
    private AuthenticationManager authenticationManager; // grant_type password를 사용하려면 필수
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserDetailsService service;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientDetailsService clientDetailsService;

    /*
    * AuthorizationInitializer 에서 발급하는 oauth 토큰들을 저장하는 저장소이다
    * token store로 JWTTokenStore를 사용하겠다
    * */

    /* JWT 디코딩 하기 위한 설정 */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {

        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("123");
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

//        client.inMemory()
//                .withClient(clientID)
//                .secret(passwordEncoder.encode(clientSecret))
//                .redirectUris("http://localhost:8080/oauth2/callback")
//                .authorizedGrantTypes("authorization_code", "password", "refresh_token")
//                .accessTokenValiditySeconds(60)
//                .refreshTokenValiditySeconds(6*60*60)
//                .scopes("trust")
//                .scopes ("read", "write")
//                .autoApprove(true);
    }

    // OAuth2 서버가 작동하기 위한 Endpoint 에 대한 정보를 설정
    // 인가 방법과 토큰 엔드포인트 설정 및 토큰 서비스를 정의합니다.
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoint) throws Exception {

        endpoint
                .authenticationManager(authenticationManager) // authenticationManager - password 값으로 사용자를 인증하고 인가
                .tokenStore(tokenStore()) // tokenStore - token이 저장될 기본 store를 정의
                .userDetailsService(service) // userDetailsService - 사용자를 인증하고 인가하는 서비스를 설정
                .accessTokenConverter(jwtAccessTokenConverter()); // accessTokenConverter - access token을 jwt 토큰으로 변환하기 위해 사용하며 jwtSecret 키를 통해 jwt 토큰을 설정

//                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE) allowedTokenEndpointRequestMethods - token endpoint를 사용할 때 허용할 method들을 설정
//                .tokenEnhancer(jwtAccessTokenConverter) tokenEnhancer - access token 추가 설정
    }
}
