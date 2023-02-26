package com.smilegate.devpet.appserver.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter @Setter
@RequiredArgsConstructor
public class UserInfo{
    private long userId;
    private String username;
    private List<String> scope;
    private Long exp;
    private List<String> authorities;
    private String jti;
    private String client_id;
}
