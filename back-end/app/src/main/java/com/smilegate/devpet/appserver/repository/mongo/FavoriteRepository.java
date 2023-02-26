package com.smilegate.devpet.appserver.repository.mongo;

import com.smilegate.devpet.appserver.model.Favorite;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FavoriteRepository extends MongoRepository<Favorite,Long> {
    public List<Favorite> findAllByPostIdAndIsFavoriteIsTrue(Long postId);
}
