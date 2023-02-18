package com.example.oauth.repository;

import com.example.oauth.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VUserRepository extends JpaRepository<User,Long> {

//    @Query("select a from PET_USER a where a.id=:userId")
//    User findById(String id);

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

}
