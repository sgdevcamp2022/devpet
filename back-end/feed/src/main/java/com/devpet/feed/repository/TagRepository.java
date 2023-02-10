package com.devpet.feed.repository;

import com.devpet.feed.entity.Tag;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface TagRepository extends Neo4jRepository<Tag, String> {

}
