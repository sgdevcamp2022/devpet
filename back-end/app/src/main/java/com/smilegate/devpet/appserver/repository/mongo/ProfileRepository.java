package com.smilegate.devpet.appserver.repository.mongo;

import com.smilegate.devpet.appserver.model.Profile;
import com.smilegate.devpet.appserver.model.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProfileRepository extends MongoRepository<Profile,Long> {
    public Optional<Profile> findByUsername(String username);
    public List<Profile> findByUsernameIn(List<String> usernames);
}
