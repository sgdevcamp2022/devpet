package com.smilegate.devpet.appserver.service;

import com.smilegate.devpet.appserver.model.UserInfo;
import org.springframework.stereotype.Service;

@Service
public class JwtConverterService {

    public UserInfo getUserInfo(String jwt)
    {
        return new UserInfo();
    }
}
