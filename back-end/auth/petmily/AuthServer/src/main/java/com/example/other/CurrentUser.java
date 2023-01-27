package com.example.other;

import com.example.shoh_oauth.data.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Collection;

public class CurrentUser extends org.springframework.security.core.userdetails.User {
    public CurrentUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
    public CurrentUser(User user, Collection<? extends GrantedAuthority> authorities) {
        this(user.getUsername(),user.getPassword(),authorities);
    }
    public CurrentUser(User user, GrantedAuthority...authorities) {
        this(user.getUsername(),user.getPassword(),Arrays.asList(authorities));
    }
}
