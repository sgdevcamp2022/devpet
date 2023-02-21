package com.smilegate.devpet.appserver.model;

import lombok.*;

import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoDto {
    private String userId;
    private String nickname;
    private String birth;
    private String address;
    private String gender;
    private Set<FollowRequest> followers;
    private Set<PostRelationRequest> postRelationRequests;
}
