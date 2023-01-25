package com.smilegate.devpet.appserver.service;

import com.smilegate.devpet.appserver.model.LongDatabaseSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SequenceGeneratorService {

    private MongoOperations mongoOperations;

    @Autowired
    public SequenceGeneratorService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public long longSequenceGenerate(String seqName) {

        LongDatabaseSequence counter = mongoOperations.findAndModify(query(Criteria.where("_id").is(seqName)),
                new Update().inc("seq",1), FindAndModifyOptions.options().returnNew(true).upsert(true),
                LongDatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;

    }
}
