package com.devpet.feed.repository;

import com.devpet.feed.data.node.PetInfo;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends Neo4jRepository<PetInfo, String> {


    PetInfo findByPetId(String petId);

    @Query("match(p:PetInfo{petId : $petId})" + "DETACH DELETE p")
    void deletePet(@Param("petId") String petId);

    @Query("match(u:UserInfo{userId : $userId})" +
            "match(p:PetInfo{petId : $petId})" +
            "create (u)-[:RAISE]->(p)")
    void raisePet(@Param("userId") String userId , @Param("petId") String petId);

    @Query("match(u:UserInfo{userId : $userId})-[r:RAISE]->(p:PetInfo{petId : $petId})" + "delete r")
    void raisePetCancel(@Param("userId") String userId, @Param("petId") String petId);

}