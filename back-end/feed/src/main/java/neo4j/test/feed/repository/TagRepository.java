package neo4j.test.feed.repository;


import neo4j.test.feed.model.entity.Tag;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface TagRepository extends Neo4jRepository<Tag, String> {

}
