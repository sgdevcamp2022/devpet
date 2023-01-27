package com.example.shoh_oauth.service;

import com.example.shoh_oauth.data.entity.User;
import com.example.shoh_oauth.data.entity.UserDetailsImpl;
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
        User findUser = vUserRepository.findByUsername(username).get();

        log.info(findUser.getUsername());

        if (findUser==null)
            throw new UsernameNotFoundException("사용자 정보를 찾을 수 없습니다.");

        UserDetailsImpl userDetails = UserDetailsImpl.builder()
                .id(findUser.getId())
                .username(findUser.getUsername())
                .nickname(findUser.getNickname())
                .password(findUser.getPassword())
                .type(findUser.getType())
                .authorities(findUser.getAuthorities())
                .build();


        //GrantedAuthority authority = new SimpleGrantedAuthority("USER");
        //return new CurrentUser(findUser,authority);
        return  userDetails;
    }
}
