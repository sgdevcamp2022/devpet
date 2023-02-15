package com.smilegate.devpet.appserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Join {
    private Long id;
    private UserInfo userInfo;

    public Join(UserInfo userInfo){
        this.userInfo = userInfo;
    }
}
