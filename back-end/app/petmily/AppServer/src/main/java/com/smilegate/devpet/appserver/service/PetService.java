package com.smilegate.devpet.appserver.service;

import com.smilegate.devpet.appserver.model.Feed;
import com.smilegate.devpet.appserver.model.Pet;
import com.smilegate.devpet.appserver.model.UserInfo;
import com.smilegate.devpet.appserver.repository.mongo.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final SequenceGeneratorService petSequenceGeneratorService;
    private final MongoOperations mongoOperations;
    public Pet postPet(Pet pet)
    {
        pet.setPetId(petSequenceGeneratorService.longSequenceGenerate(Pet.SEQUENCE_NAME));
        Pet result = petRepository.save(pet);
        return result;
    }
    public List<Pet> postAllPet(List<Pet> petList)
    {
        List<Pet> petStream = petList.stream().filter(item->item.getPetId()==null).collect(Collectors.toList());
        AtomicLong lastSeq = new AtomicLong(petSequenceGeneratorService.longSequenceBulkGenerate(Feed.SEQUENCE_NAME, (int) petStream.size()));
        petStream.forEach(item->{
            item.setPetId(lastSeq.get());
            lastSeq.getAndIncrement();
        });
        List<Pet> result = new ArrayList<>(mongoOperations.insert(petList,"pet"));
        return result;
    }
    public List<Pet> getUserPetList(Long profileId)
    {
       List<Pet> result = petRepository.findByProfileId(profileId);
       return result;
    }

    public Pet getUserPet(Long petId)
    {
       Pet pet = petRepository.findById(petId).orElseThrow(NullPointerException::new);
       return pet;
    }
}
