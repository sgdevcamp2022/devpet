package com.smilegate.devpet.appserver.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JoinGroupRequest {
    private String userId;
    private String groupName;
}
