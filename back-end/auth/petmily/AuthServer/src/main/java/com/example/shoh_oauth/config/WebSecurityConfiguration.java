package com.example.shoh_oauth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SimpleUrlAuthenticationFailureHandler authenticationFailureHandler() {
        SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler("/oauth/error");
        failureHandler.setAllowSessionCreation(false);
        return failureHandler;
    }

    /*
       인증 토큰을 관리하는 클래스.
       리소스에 대한 요청이 들어오면 AuthenticationManager 는 Authenticate 메서드를 호출해 후속요청에 사용할 권한 부여 인스턴스를 가져옴
       Resource Owner Password Credentials Grant 방식을 사용하기 위해서는 AuthenticationManager 에 의존성주입을 설정해야한다
    */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**", "/images/**", "/oauth/uncache_approvals", "/oauth/cache_approvals", "/templates/*");
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .formLogin().disable()
//                .csrf().disable()
//                .httpBasic().disable()
//                .authorizeRequests()
//                    .antMatchers("/oauth/sign-up").permitAll()
//                    .antMatchers("/oauth/token").permitAll()
//                    .antMatchers("/oauth/kakao").permitAll()
////                    .antMatchers("/profile/**").permitAll()
//                    .anyRequest().authenticated();
//    }
}