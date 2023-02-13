package com.devpet.feed.model.relationship;

import com.devpet.feed.model.dto.ScoreDto;
import com.devpet.feed.model.entity.PostInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

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
}
