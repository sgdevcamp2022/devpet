package com.devpet.feed.model.relationship;

import com.devpet.feed.model.dto.ScoreDto;
import com.devpet.feed.model.entity.PostInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

import java.util.Objects;

@RelationshipProperties
@Getter @Setter
@RequiredArgsConstructor
public class Recommend {
    @Id @GeneratedValue
    private Long id;

    @Property
    private double score;
    @TargetNode
    private PostInfo postInfo;

    public Recommend(PostInfo postInfo, ScoreDto scoreDto){
        this.postInfo = postInfo;
        this.score = scoreDto.getScore();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recommend recommend = (Recommend) o;
        return Objects.equals(postInfo.getPostId(), recommend.postInfo.getPostId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(postInfo.getPostId());
    }
}
