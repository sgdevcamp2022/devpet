package com.smilegate.devpet.appserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Follow {
    private Long id;
    private UserInfo userInfo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Follow follow = (Follow) o;
        return Objects.equals(userInfo.getUserId(), follow.userInfo.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(userInfo.getUserId());
    }
}
