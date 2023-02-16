package com.devpet.feed.repository;

import com.devpet.feed.model.entity.PetInfo;
import com.devpet.feed.model.entity.UserInfo;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PetRepository extends Neo4jRepository<PetInfo, String> {

    @Query("MATCH (m:PetInfo {petId: $petId}) " + "RETURN m" )
    Optional<PetInfo> findByPetId(@Param("petId") String petId);

    @Query("MATCH (m:PetInfo {petName: $petName}) " + "RETURN m" )
    Optional<PetInfo> findByPetName(@Param("petName") String petName);

    @Query("match(p:PetInfo{petId : $petId})" + "DETACH DELETE p")
    void deletePet(@Param("petId") String petId);


    @Query("match(u:UserInfo{userId : $userId})-[r:PET]->(p:PetInfo{petId : $petId})" + "delete r")
    void raisePetCancel(@Param("userId") String userId, @Param("petId") String petId);

    @Query("match(u:UserInfo{userId: $userId}) " +
            "match(p:PetInfo{petId : $petId}) " +
            "WHERE EXISTS((u)-[:PET]->(p)) " + "RETURN p")
    PetInfo checkPet(@Param("userId") String userId, @Param("petId") String petId);
//    @Query("MATCH (m:PetInfo {petName: $petName}) " + "RETURN m" )
//    Optional<PetInfo> findByPetName(String petName);
}
