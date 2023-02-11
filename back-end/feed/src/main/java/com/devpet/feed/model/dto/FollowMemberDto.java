package com.devpet.feed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FollowMemberDto {
    private String username;
    private String followUser;
}
