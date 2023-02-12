package neo4j.test.feed.model.dto;

import lombok.*;
import neo4j.test.feed.model.entity.UserInfo;
import neo4j.test.feed.model.relationship.Follow;
import neo4j.test.feed.model.relationship.Post;

import java.util.Set;

@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
public class UserInfoDto {

    private String userId;
    private String nickname;
    private String birth;
    private String address;
    private String gender;

    private Set<Follow> followers;
    private Set<Post> posts;

    public UserInfoDto(UserInfo userInfo) {
        this.userId = userInfo.getUserId();
        this.nickname = userInfo.getNickname();
        this.birth = userInfo.getBirth();
        this.address = userInfo.getAddress();
        this.gender = userInfo.getGender();
        this.followers = userInfo.getFollower();
    }

    @Builder
    public UserInfoDto(String userId, String nickname, String birth, String address, String gender) {

        this.userId = userId;
        this.nickname = nickname;
        this.birth = birth;
        this.address = address;
        this.gender = gender;
    }

}
