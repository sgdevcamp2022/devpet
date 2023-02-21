package com.smilegate.devpet.appserver.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Like {
    private Long id;
    private UserInfo userInfo;

    public Like(UserInfo userInfo){
        this.userInfo = userInfo;
    }

}
