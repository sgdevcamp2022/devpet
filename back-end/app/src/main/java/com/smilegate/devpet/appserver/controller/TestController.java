package com.smilegate.devpet.appserver.controller;

import com.mongodb.client.model.Aggregates;
import com.smilegate.devpet.appserver.config.TestOperation;
import com.smilegate.devpet.appserver.model.Comment;
import com.smilegate.devpet.appserver.model.Feed;
import com.smilegate.devpet.appserver.model.UserInfo;
import com.smilegate.devpet.appserver.repository.redis.NewPostRedisRepository;
import com.smilegate.devpet.appserver.service.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Shape;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final MongoTemplate mongoTemplate;
    @GetMapping("pingpong")
    public Long pingpong(UserInfo info)
    {
        return info.getUserId();
    }

    @PostMapping
    @Transactional
    public List<Feed> test(@RequestBody Map<String,Long> request,UserInfo userInfo)
    {
        Long id = request.get("postId");
        Long userId = userInfo.getUserId();
        LookupOperation commentLookupOperation = Aggregation.lookup("comment","postId","_id","comments");
        Aggregation commentPipeLineAggregation = newAggregation(
                Aggregation.limit(3),
                Aggregation.sort(Sort.by("createdAt"))
        );
        TestOperation operation = new TestOperation(commentLookupOperation,commentPipeLineAggregation);
        Aggregation aggregation = newAggregation(
//                Aggregation.match(Criteria.where("_id").is(id).and("usage").is(Boolean.TRUE)),
                Aggregation.match(getNearFeedQuery(null,null,null,null)),
                operation,
                Aggregation.lookup("favorite","postId","_id","isFavorite"),
                skip(0L),
                limit(5)
        );
        AggregationResults<Feed> result = mongoTemplate.aggregate(aggregation,"feed",Feed.class);
        return result.getMappedResults();
    }

    private Criteria getNearFeedQuery(String content, Integer category, Shape shape, Pageable pageable)
    {
        Criteria result = Criteria.where("usage").is(Boolean.TRUE);
        if (content != null)
            result.and("content").regex(content);
        if (category != null)
            result.and("location.category").is(category);
        if (shape != null)
            result.and("location.coord").within(shape);
        return result;
    }
}
