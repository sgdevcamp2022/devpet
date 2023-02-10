package com.devpet.feed.data.node;

import com.devpet.feed.data.relationship.Follow;
import com.devpet.feed.data.relationship.Like;
import com.devpet.feed.data.relationship.Pet;
import com.devpet.feed.data.relationship.Recommended;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.driver.internal.shaded.reactor.util.annotation.NonNull;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node
@Getter
@Setter
@NoArgsConstructor
public class UserInfo {

    // 이메일
    @Id
    @NonNull
    private String userId;

    @Property
    private String nickname;

    @Property
    private String birth;

    @Property
    private String address;

    @Property
    private String gender;


    @Relationship(type="FOLLOW", direction = Relationship.Direction.INCOMING)
    private Set<Follow> follower = new HashSet<>();

    @Relationship(type="FOLLOW", direction = Relationship.Direction.OUTGOING)
    private Set<Follow> following = new HashSet<>();


    @Relationship(type="LIKE", direction = Relationship.Direction.OUTGOING)
    private Set<Like> like = new HashSet<>();

    @Relationship(type = "PET", direction = Relationship.Direction.OUTGOING)
    private Set<Pet> pet = new HashSet<>();

    @Relationship(type = "RECOMMENDED", direction = Relationship.Direction.OUTGOING)
    private Set<Recommended> recommended = new HashSet<>();

    @Builder
    public UserInfo(String userId, String nickname, String birth, String address, String gender) {

        this.userId = userId;
        this.nickname = nickname;
        this.birth = birth;
        this.address = address;
        this.gender = gender;
    }
}
