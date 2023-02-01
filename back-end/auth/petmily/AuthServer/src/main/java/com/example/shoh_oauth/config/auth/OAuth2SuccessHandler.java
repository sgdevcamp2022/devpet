package com.example.shoh_oauth.config.auth;

import com.example.shoh_oauth.data.dto.UserDto;
import com.example.shoh_oauth.data.entity.User;
import com.example.shoh_oauth.repository.VUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRequestMapper userRequestMapper;
    private final VUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        UserDto userDto = userRequestMapper.getUser(oAuth2User);

        // 최초 로그인이라면 회원가입 처리를 한다.
        log.info(userDto.getUsername());
        log.info(userDto.getName());
        if (!userRepository.existsByUsername(userDto.getUsername())) {
           User user = User.builder()
                   .username(userDto.getUsername())
                   .name(userDto.getName())
                   .password(passwordEncoder.encode(userDto.getPassword()))
                   .build();
           userRepository.save(user);
        }

    }
}