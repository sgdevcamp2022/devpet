package com.smilegate.devpet.appserver.config;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TestOperation implements AggregationOperation {
    private final LookupOperation lookupOperation;
    private final Aggregation pipelineAggregation;

    @Override
    public Document toDocument(AggregationOperationContext context) {
        return lookupOperation.toDocument(context);
    }

    @Override
    public List<Document> toPipelineStages(AggregationOperationContext context) {
        List<Document> lookUpPipelineStages = lookupOperation.toPipelineStages(context);
        Document lookup = lookUpPipelineStages.stream().filter(item -> item.containsKey(lookupOperation.getOperator()))
                .findFirst().orElseThrow(RuntimeException::new);
        lookup.append("pipeline", pipelineAggregation.getPipeline()
                .getOperations().stream().flatMap(aggregationOperation ->
                        aggregationOperation.toPipelineStages(context).stream())
                .collect(Collectors.toList()));
        return lookUpPipelineStages;
    }

    @Override
    public String getOperator() {
        return lookupOperation.getOperator();
    }
}
