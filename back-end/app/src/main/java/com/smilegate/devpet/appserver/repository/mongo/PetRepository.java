package com.smilegate.devpet.appserver.repository.mongo;

import com.smilegate.devpet.appserver.model.Pet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PetRepository extends MongoRepository<Pet,Long> {
    public List<Pet> findByProfileId(Long profileId);
}
