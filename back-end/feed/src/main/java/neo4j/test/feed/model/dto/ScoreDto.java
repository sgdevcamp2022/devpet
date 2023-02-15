package neo4j.test.feed.model.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ScoreDto {
    private String userId;
    private String postId;
    private double score;


}