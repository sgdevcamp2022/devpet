package com.smilegate.devpet.appserver.repository.mongo;

import com.smilegate.devpet.appserver.model.TestModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends MongoRepository<TestModel,Long> {

}