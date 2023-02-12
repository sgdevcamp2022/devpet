package neo4j.test.feed.model.relationship;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import neo4j.test.feed.model.dto.ScoreDto;
import neo4j.test.feed.model.entity.PostInfo;
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
