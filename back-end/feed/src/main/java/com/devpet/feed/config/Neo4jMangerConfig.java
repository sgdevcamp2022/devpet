package com.devpet.feed.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Config;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Neo4j native query 사용 설정
 */
@Configuration
@RequiredArgsConstructor
@Getter
public class Neo4jMangerConfig {
    @Value("${spring.neo4j.uri}")
    String uri;
    @Value("${spring.neo4j.authentication.username}")
    String username;
    @Value("${spring.neo4j.authentication.password}")
    String password;
    private final static Config config = Config.defaultConfig();
    private Driver driver;

    @Bean
    public Driver Neo4jManager(@Value("${spring.neo4j.uri}") String uri,
                               @Value("${spring.neo4j.authentication.username}") String username,
                               @Value("${spring.neo4j.authentication.password}") String password) {
        return driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password), config);
    }

}
