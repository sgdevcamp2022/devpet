package com.devpet.feed.entity;

import com.devpet.feed.dto.UserInfoDto;
import com.devpet.feed.relationship.Follow;
import com.devpet.feed.relationship.Post;
import com.devpet.feed.relationship.Recommend;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;
import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Getter
@Setter
@Node
@NoArgsConstructor
public class UserInfo {

    @Id //
    private String userId;
    @Property
    private String nickname;
    @Property
    private String birth;
    @Property
    private String address;
    @Property
    private String gender;
    @Relationship(type = "Follow", direction = INCOMING) //RelationShip 설정
    private Set<Follow> followers;
    @Relationship(type = "Follow", direction = OUTGOING) //RelationShip 설정
    private Set<Follow> follows;
    @Relationship(type = "has_Post", direction = OUTGOING)
    private Set<Post> posts;

    @Relationship(type = "has_Recommended" , direction = OUTGOING)
    private Set<Recommend> recommends;

    public UserInfo(UserInfoDto dto){
        this.userId = dto.getUserId();
        this.nickname= dto.getNickname();
        this.birth = dto.getBirth();
        this.address = dto.getAddress();
        this.gender = dto.getGender();
        this.followers = dto.getFollowers();
        this.posts = dto.getPosts();
    }

    public UserInfo(UserInfo dto){
        this.userId = dto.getUserId();
        this.nickname= dto.getNickname();
        this.birth = dto.getBirth();
        this.address = dto.getAddress();
        this.gender = dto.getGender();
        this.followers = dto.getFollowers();
        this.posts = dto.getPosts();
    }


}
