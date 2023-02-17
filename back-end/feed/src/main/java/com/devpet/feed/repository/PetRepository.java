package com.devpet.feed.repository;

import com.devpet.feed.model.entity.PetInfo;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PetRepository extends Neo4jRepository<PetInfo, Long> {

    // petId를 통해 펫 노드 찾기
    @Query("MATCH (m:PetInfo {petId: $petId}) " + "RETURN m" )
    Optional<PetInfo> findByPetId(@Param("petId") Long petId);

    @Query("MATCH (m:PetInfo {petName: $petName}) " + "RETURN m" )
    Optional<PetInfo> findByPetName(@Param("petName") String petName);

    // 펫 노드 삭제 동시에 펫과 연결된 관계들도 삭제
    @Query("match(p:PetInfo{petId : $petId})" + "DETACH DELETE p")
    void deletePet(@Param("petId") Long petId);

    @Query("match(u:UserInfo{userId: $userId}) " +
            "match(p:PetInfo{petId : $petId}) " +
            "WHERE EXISTS((u)-[:PET]->(p)) " + "RETURN p")
    PetInfo checkPet(@Param("userId") String userId, @Param("petId") Long petId);
}