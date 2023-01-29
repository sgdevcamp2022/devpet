package com.example.shoh_oauth.service;

import com.example.shoh_oauth.data.entity.User;
import com.example.shoh_oauth.data.entity.UserDetailsImpl;
import com.example.shoh_oauth.exception.DataNotFoundException;
import com.example.shoh_oauth.repository.VUserRepository;
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
        User findUser = vUserRepository.findByUsername(username).orElseThrow(() -> new DataNotFoundException("이메일을 잘못 입력했거나 사용자가 존재하지 않습니다"));

        log.info(findUser.getUsername());

        UserDetailsImpl userDetails = UserDetailsImpl.builder()
                .id(findUser.getId())
                .username(findUser.getUsername())
                .nickname(findUser.getNickname())
                .password(findUser.getPassword())
                .authorities(findUser.getAuthorities())
                .build();


        //GrantedAuthority authority = new SimpleGrantedAuthority("USER");
        //return new CurrentUser(findUser,authority);
        return  userDetails;
    }
}
