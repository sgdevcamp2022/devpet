package com.devpet.feed.repository;


import com.devpet.feed.config.Neo4jMangerConfig;
import com.devpet.feed.model.dto.LikePostDto;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.exceptions.Neo4jException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class Neo4jRepository implements AutoCloseable {

    private static final Logger LOGGER = Logger.getLogger(Neo4jRepository.class.getName());
    private final Driver driver;

    public void saveLikeAll(List<LikePostDto> likePostDtoList) {
        StringBuilder queryString = new StringBuilder();
        StringBuilder mergeString = new StringBuilder();
        for (int i = 0; i < likePostDtoList.size(); i++) {
            queryString.append("match (user")
                    .append(i)
                    .append(":UserInfo {userId:\"")
                    .append(likePostDtoList.get(i)
                            .getUserId())
                    .append("\"})\n")
                    .append("match (post")
                    .append(i)
                    .append(":PostInfo {postId:\"")
                    .append(likePostDtoList.get(i).getPostId())
                    .append("\"})\n");
            mergeString.append("merge (user")
                    .append(i)
                    .append(")-[:LIKE]-> (post")
                    .append(i)
                    .append(")\n");
        }
        queryString.append(mergeString);
        Query query = new Query(
                queryString.toString()
        );

        try (var session = driver.session(SessionConfig.forDatabase("feed"))) {
//            session.writeTransaction(tx -> {
//                List<Record> result =
//                        tx.run(query).list();
//                return result;
                session.writeTransaction(tx -> {
                    return tx.run(query);
            });

        } catch (Neo4jException ex) {
            LOGGER.log(Level.SEVERE, query + " raised an exception", ex);
            throw ex;
        }
    }

    public void deleteLikeAll(List<LikePostDto> likePostDtoList) {
        StringBuilder queryString = new StringBuilder();
        StringBuilder deleteString = new StringBuilder();
        for (int i = 0; i < likePostDtoList.size(); i++) {
            queryString.append("match (user")
                    .append(i).append(":UserInfo {userId:\"")
                    .append(likePostDtoList.get(i)
                            .getUserId())
                    .append("\"})\n")
                    .append("match (post")
                    .append(i).append(":PostInfo {postId:\"")
                    .append(likePostDtoList.get(i).getPostId())
                    .append("\"})\n")
                    .append("match (user")
                    .append(i).append(")-[like")
                    .append(i).append(":LIKE]->(post")
                    .append(i).append(")\n");
            deleteString.append("delete ")
                    .append("like")
                    .append(i).append("\n");
        }
        queryString.append(deleteString);
        Query query = new Query(
                queryString.toString()
        );

        try (var session = driver.session(SessionConfig.forDatabase("feed"))) {
            session.writeTransaction(tx -> {
                return tx.run(query);
            });

        } catch (Neo4jException ex) {
            LOGGER.log(Level.SEVERE, query + " raised an exception", ex);
            throw ex;
        }
    }


    public Neo4jRepository(Neo4jMangerConfig config) {
        this.driver = config.getDriver();
    }

    @Override
    public void close() throws Exception {
        driver.close();
    }

}

