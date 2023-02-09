package com.example.oauth.service;

import com.example.oauth.data.dto.SignUpRequest;
import com.example.oauth.data.entity.User;
import com.example.oauth.exception.DataNotFoundException;
import com.example.oauth.exception.DuplicateUserException;
import com.example.oauth.repository.VUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void saveKaKaoUser(SignUpRequest signUpRequest) {

        User user = User.builder()
                .username(signUpRequest.getUsername())
                .name(signUpRequest.getName())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .build();

        userRepository.save(user);
    }

    public void checkDuplicateNickname(String nickname) {
        if (userRepository.existsByNickname(nickname))
            throw new DuplicateUserException("4001");
    }

    public void checkDuplicateEmail(String username) {
        if (userRepository.existsByUsername(username))
            throw new DuplicateUserException("4000");
    }

    // 카카오 프로필 정보(1차) 일때 이메일 중복 체크
    public void checkDuplicateKaKaoEmail(String username) {
        if (userRepository.existsByUsername(username))
            throw new DuplicateUserException("4009");
    }

    // 카카오 로그인 진행 후 추가 정보 기입해서 기존에 자동 회원가입 되어 있는 카카오 유저 정보 업데이트
    public void saveKaKaoUserLast (SignUpRequest signUpRequest) {

        checkDuplicateNickname(signUpRequest.getNickname());
        updateKaKaoUser(signUpRequest);
    }

    public void updateKaKaoUser (SignUpRequest signUpRequest) {
            User user = userRepository.findByUsername(signUpRequest.getUsername()).orElseThrow(() -> new DataNotFoundException("4005"));
            user.setAge(signUpRequest.getAge());
            user.setNickname(signUpRequest.getNickname());
            user.setProvider(signUpRequest.getProvider());
            user.setPhone(signUpRequest.getPhone());
            user.setGender(signUpRequest.getGender());
            userRepository.save(user);
    }
}