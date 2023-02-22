package com.devpet.feed.repository;


import com.devpet.feed.model.dto.LikePostDto;
import com.devpet.feed.model.dto.PetInfoDto;
import org.neo4j.driver.*;
import org.neo4j.driver.exceptions.Neo4jException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * bulk 연산 위주 Native Query
 */

public class Neo4jRepo implements AutoCloseable {
//    @Value("${spring.neo4j.uri}")
//    String uri;
//    @Value("${spring.neo4j.authentication.username}")
//    String username;
//    @Value("${spring.neo4j.authentication.password}")
//    String password;
//    private final static Config config = Config.defaultConfig();

    private final Driver driver;
    public Neo4jRepo(String uri, String user, String password, Config config){
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password), config);
    }
    @Override
    public void close() throws Exception {
        driver.close();
    }

//    public Driver Neo4jManager() {
//        return driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password), config);
//    }

    private static final Logger LOGGER = Logger.getLogger(Neo4jRepo.class.getName());

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


//    public Neo4jRepo(Neo4jMangerConfig config) {
//        this.driver = config.getDriver();
//    }


    public void savePetAll(List<PetInfoDto> petList) {
        StringBuilder queryString = new StringBuilder();
        StringBuilder mergeString = new StringBuilder();
        StringBuilder tagString = new StringBuilder();
        StringBuilder createString = new StringBuilder();
        long count = 0;
        long petCount = 0;
        for (int i = 0; i < petList.size(); i++) {
            queryString.append("match (user")
                    .append(i)
                    .append(":UserInfo {userId:\"")
                    .append(petList.get(i).getUserId())
                    .append("\"})\n");
            createString.append("merge (pet")
                    .append(i)
                    .append(":PetInfo {petId: ")
                    .append(petList.get(i).getPetId())
                    .append(", petName: \"")
                    .append(petList.get(i).getPetName())
                    .append("\", petBirth: \"")
                    .append(petList.get(i).getPetBirth())
                    .append("\", petSpecies: \"")
                    .append(petList.get(i).getPetSpecies())
                    .append("\"})\n");
            mergeString.append("merge (user")
                    .append(i)
                    .append(")-[:PET]-> (pet")
                    .append(i)
                    .append(")\n");
            for(int j = 0; j < petList.get(i).getTags().size(); j++) {
                tagString.append("merge (tag")
                        .append(count)
                        .append(":Tag {tagName:\"")
                        .append(petList.get(i).getTags().get(j).getTagName())
                        .append("\"})\n")

                        .append("merge (pet")
                        .append(petCount)
                        .append(")-[:TAGD]->(tag")
                        .append(count)
                        .append(")\n");
                count += 1;
            }
            petCount += 1;
        }
        queryString.append(createString);
        queryString.append(mergeString);
        queryString.append(tagString);
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

}

