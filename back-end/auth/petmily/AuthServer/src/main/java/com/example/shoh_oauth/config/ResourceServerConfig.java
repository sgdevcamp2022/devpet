package com.example.shoh_oauth.config;

import com.example.shoh_oauth.config.auth.CustomOAuth2UserService;
import com.example.shoh_oauth.config.auth.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

//    private final CustomOAuth2UserService oAuth2UserService;
//    private final OAuth2SuccessHandler oAuth2SuccessHandler;
//
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
        OAuth2AuthenticationEntryPoint authenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
        authenticationEntryPoint.setExceptionTranslator(resourceWebResponseExceptionTranslator());
        resources
                .authenticationEntryPoint(authenticationEntryPoint);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.anonymous().disable()
                .authorizeRequests()
                    //.antMatchers(HttpMethod.OPTIONS).permitAll()
                    .antMatchers("/map/**").authenticated()
                    .antMatchers("/proflil/**").authenticated()
                    .antMatchers("/post/**").authenticated()
                    .antMatchers("/api/**").authenticated()
                    .antMatchers("/users/**").hasAnyRole("USER", "ADMIN")
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new OAuth2AccessDeniedHandler());

    }

    @Bean
    public WebResponseExceptionTranslator resourceWebResponseExceptionTranslator() {
        return new DefaultWebResponseExceptionTranslator() {

            @Override
            public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
                Map responseMap = new HashMap();

                responseMap.put("message", "접근 권한이 없거나 토큰이 만료 되었습니다.");
                return new ResponseEntity(responseMap, HttpStatus.UNAUTHORIZED);
            }
        };
    }
}