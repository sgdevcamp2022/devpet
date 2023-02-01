package com.example.shoh_oauth.service;

import com.example.shoh_oauth.data.dto.SignUpRequest;
import com.example.shoh_oauth.data.entity.User;
import com.example.shoh_oauth.exception.DataNotFoundException;
import com.example.shoh_oauth.exception.DuplicateUserException;
import com.example.shoh_oauth.exception.SimpleFieldError;
import com.example.shoh_oauth.repository.VUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final VUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void saveUser(SignUpRequest signUpRequest) {

        checkDuplicateEmail(signUpRequest.getUsername());
        checkDuplicateNickname(signUpRequest.getNickname());

        User user = User.builder()
                .username(signUpRequest.getUsername())
                .name(signUpRequest.getName())
                .nickname(signUpRequest.getNickname())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .age(signUpRequest.getAge())
                .gender(signUpRequest.getGender())
                .phone(signUpRequest.getPhone())
                .provider(null)
                .build();

        userRepository.save(user);
    }

    public void checkPassword(Map<String, String> parameters) {
        User user = userRepository.findByUsername(String.valueOf(parameters.get("username"))).orElseThrow(() -> new DataNotFoundException("이메일을 잘못 입력했거나 사용자가 존재하지 않습니다"));

        if (!passwordEncoder.matches(String.valueOf(parameters.get("password")), user.getPassword())) {
            throw new DataNotFoundException("비밀번호가 일치하지 않습니다.");
        }

    }

    public void checkDuplicateNickname(String nickname) {
        if (userRepository.existsByNickname(nickname))
            throw new DuplicateUserException("사용중인 닉네임 입니다.", new SimpleFieldError("nickname", "사용중인 닉네임 입니다."));
    }

    public void checkDuplicateEmail(String username) {
        if (userRepository.existsByUsername(username))
            throw new DuplicateUserException("사용중인 이메일 입니다.", new SimpleFieldError("email", "사용중인 이메일 입니다."));
    }

    public void checkLoginEmail(Map<String, String> parameters) {
        if ( parameters.get("provider") != null ) {
            checkDuplicateNickname(String.valueOf(parameters.get("nickname")));
            updateKaKaoLoginInfo(parameters);
        }
    }

    public void updateKaKaoLoginInfo(Map<String, String> parameters) {
           User user = userRepository.findByUsername(String.valueOf(parameters.get("username"))).orElseThrow(() -> new DataNotFoundException("이메일을 잘못 입력했거나 사용자가 존재하지 않습니다"));
           user.setNickname(String.valueOf(parameters.get("nickname")));
           user.setGender(String.valueOf(parameters.get("gender")));
           user.setAge(String.valueOf(parameters.get("age")));
           user.setPhone(String.valueOf(parameters.get("phone")));
           user.setProvider(String.valueOf(parameters.get("provider")));
           userRepository.save(user);
    }

}