package neo4j.test.feed.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import neo4j.test.feed.model.dto.UserInfoDto;
import neo4j.test.feed.model.relationship.*;
import org.neo4j.driver.internal.shaded.reactor.util.annotation.NonNull;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;
import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Getter
@Setter
@Node
@NoArgsConstructor
public class UserInfo {

    // 이메일
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

    @Relationship(type="FOLLOW", direction = INCOMING)
    private Set<Follow> follower = new HashSet<>();
    @Relationship(type="FOLLOW", direction = OUTGOING)
    private Set<Follow> following = new HashSet<>();

    @Relationship(type = "has_Post", direction = OUTGOING)
    private Set<Post> posts;
    @Relationship(type = "has_Recommended" , direction = OUTGOING)
    private Set<Recommend> recommends;

    @Relationship(type="LIKE", direction = OUTGOING)
    private Set<Like> like = new HashSet<>();
    @Relationship(type = "PET", direction = OUTGOING)
    private Set<Pet> pet = new HashSet<>();
    @Relationship(type = "RECOMMENDED", direction = Relationship.Direction.OUTGOING)
    private Set<Recommend> recommended = new HashSet<>();

    public UserInfo(UserInfoDto dto){
        this.userId = dto.getUserId();
        this.nickname= dto.getNickname();
        this.birth = dto.getBirth();
        this.address = dto.getAddress();
        this.gender = dto.getGender();
        this.follower = dto.getFollowers();
        this.posts = dto.getPosts();
    }

    public UserInfo(UserInfo dto){
        this.userId = dto.getUserId();
        this.nickname= dto.getNickname();
        this.birth = dto.getBirth();
        this.address = dto.getAddress();
        this.gender = dto.getGender();
        this.follower = dto.getFollower();
        this.posts = dto.getPosts();
    }

    @Builder
    public UserInfo(String userId, String nickname, String birth, String address, String gender) {

        this.userId = userId;
        this.nickname = nickname;
        this.birth = birth;
        this.address = address;
        this.gender = gender;
    }

}
