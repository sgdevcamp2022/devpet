package com.smilegate.devpet.appserver.repository.redis;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface RedisSetRepository<K,V> {


    public void save(K keyId,V valueId);
    public void saveAll(K keyId, Collection<V> valueIds);
    public List<V> findById(K keyId, int count);

    public String keyGenerator(K keyId);
}
