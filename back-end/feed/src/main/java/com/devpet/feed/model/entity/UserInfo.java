package com.devpet.feed.model.entity;

import com.devpet.feed.model.dto.UserInfoDto;
import com.devpet.feed.model.relationship.Pet;
import com.devpet.feed.model.relationship.Follow;
import com.devpet.feed.model.relationship.Post;
import com.devpet.feed.model.relationship.Recommend;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;
import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Getter
@Setter
@Node
@NoArgsConstructor
public class UserInfo {

    @Id
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
    @Relationship(type = "PET", direction = Relationship.Direction.OUTGOING)
    private Set<Pet> pet;

    @Builder
    public UserInfo(UserInfoDto dto){

        if (dto.getUserId()!=null)
            this.userId = dto.getUserId();

        this.nickname= dto.getNickname();
        this.birth = dto.getBirth();
        this.address = dto.getAddress();
        this.gender = dto.getGender();
        this.followers = dto.getFollowers();
        this.posts = dto.getPosts();
    }
}
