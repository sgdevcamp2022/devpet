package neo4j.test.feed.repository;

import neo4j.test.feed.model.entity.PetInfo;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends Neo4jRepository<PetInfo, String> {

    @Query("MATCH (m:PetInfo {petId: $petId}) " + "RETURN m" )
    PetInfo findByPetId(@org.springframework.data.repository.query.Param("petId") String petId);

    @Query("match(p:PetInfo{petId : $petId}) " + "DETACH DELETE p")
    void deletePet(@org.springframework.data.repository.query.Param("petId") String petId);

//    @Query("match(u:UserInfo{userId : $userId})" +
//            "match(p:PetInfo{petId : $petId})" +
//            "create (u)-[:RAISE]->(p)")
//    void raisePet(@Param("userId") String userId , @Param("petId") String petId);

    @Query("match(u:UserInfo{userId : $userId})-[r:PET]->(p:PetInfo{petId : $petId}) " + "delete r")
    void raisePetCancel(@org.springframework.data.repository.query.Param("userId") String userId, @org.springframework.data.repository.query.Param("petId") String petId);

    @Query("match(u:UserInfo{userId: $userId}) " +
            "match(p:PetInfo{petId : $petId}) " +
            "WHERE EXISTS((u)-[:PET]->(p)) " + "RETURN p")
    PetInfo checkPet(@org.springframework.data.repository.query.Param("userId") String userId, @org.springframework.data.repository.query.Param("petId") String petId);
}