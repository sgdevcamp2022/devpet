package com.devpet.feed.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.DatabaseSelection;
import org.springframework.data.neo4j.core.DatabaseSelectionProvider;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class DatabaseConfig {
    /**
     * neo4j 데이터베이스 이름 결정
     * @param database
     * @return
     */
    @Bean
    DatabaseSelectionProvider databaseSelectionProvider(@Value("${spring.neo4j.database}") String database){
        return ()->{
            String neo4jVersion = System.getenv("NEO4J_VERSION");
            if(neo4jVersion == null || neo4jVersion.startsWith("5")){
                return DatabaseSelection.byName(database);
            }
            return DatabaseSelection.undecided();
        };
    }


}
