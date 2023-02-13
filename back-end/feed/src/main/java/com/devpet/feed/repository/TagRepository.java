package com.devpet.feed.repository;

import com.devpet.feed.model.entity.Tag;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface TagRepository extends Neo4jRepository<Tag, String> {

}
