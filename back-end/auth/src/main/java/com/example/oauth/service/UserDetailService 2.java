package com.example.oauth.service;

import com.example.oauth.data.entity.User;
import com.example.oauth.data.entity.UserDetailsImpl;
import com.example.oauth.exception.DataNotFoundException;
import com.example.oauth.repository.VUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final VUserRepository vUserRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(username);
        User findUser = vUserRepository.findByUsername(username).orElseThrow(() -> new DataNotFoundException("이메일을 잘못 입력했거나 사용자가 존재하지 않습니다"));

        log.info(findUser.getUsername());

        UserDetailsImpl userDetails = UserDetailsImpl.builder()
                .id(findUser.getId())
                .username(findUser.getUsername())
                .password(findUser.getPassword())
                .authorities(findUser.getAuthorities())
                .build();

        return  userDetails;
    }
}
