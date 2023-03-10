package com.example.oauth.service;

import com.example.oauth.data.dto.SignUpRequest;
import com.example.oauth.data.entity.User;
import com.example.oauth.exception.DataNotFoundException;
import com.example.oauth.exception.DuplicateUserException;
import com.example.oauth.repository.VUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
//        checkDuplicateNickname(signUpRequest.getNickname());

        User user = User.builder()
                .username(signUpRequest.getUsername())
                .name(signUpRequest.getName())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .phone(signUpRequest.getPhone())
                .provider(signUpRequest.getProvider())
                .build();

        userRepository.save(user);
    }

    public void saveKaKaoUser(SignUpRequest signUpRequest) {
        checkDuplicateKaKaoEmail(signUpRequest.getUsername());
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .name(signUpRequest.getName())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .build();
        userRepository.save(user);
        JdbcTemplate template;
    }

//    public void checkDuplicateNickname(String nickname) {
//        if (userRepository.existsByNickname(nickname))
//            throw new DuplicateUserException("4001");
//    }

    public void checkDuplicateEmail(String username) {
        if (userRepository.existsByUsername(username))
            throw new DuplicateUserException("4000");
    }

    // ????????? ????????? ??????(1???) ?????? ????????? ?????? ??????
    public void checkDuplicateKaKaoEmail(String username) {
        if (userRepository.existsByUsername(username))
            throw new DuplicateUserException("4009");
    }

    // ????????? ????????? ?????? ??? ?????? ?????? ???????????? ????????? ?????? ???????????? ?????? ?????? ????????? ?????? ?????? ????????????
//    public void saveKaKaoUserLast (SignUpRequest signUpRequest) {
//        createKaKaoUser(signUpRequest);
//        checkDuplicateNickname(signUpRequest.getNickname());
//        updateKaKaoUser(signUpRequest);
//    }
//    public void createKaKaoUser (SignUpRequest signUpRequest) {
//        checkDuplicateEmail(signUpRequest.getUsername());
//        User user = User.builder()
//                        .username(signUpRequest.getUsername())
//                        .name(signUpRequest.getName())
//                        .password(signUpRequest.getPassword())
//                        .phone(signUpRequest.getPhone()).build();
//        user.setProvider(signUpRequest.getProvider());
//        user.setPhone(signUpRequest.getPhone());
//        userRepository.save(user);
//    }
    public void updateKaKaoUser (SignUpRequest signUpRequest) {
            User user = userRepository.findByUsername(signUpRequest.getUsername()).orElseThrow(() -> new DataNotFoundException("4005"));
            user.setProvider(signUpRequest.getProvider());
            user.setPhone(signUpRequest.getPhone());
            userRepository.save(user);
    }

    public void changePassword (Long id,String password)
    {
        userRepository.findById(id).ifPresent(user->{
            user.setPassword(password);
            userRepository.save(user);
        });
    }
}