package com.example.shoh_oauth.repository;

import com.example.shoh_oauth.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VUserRepository extends JpaRepository<User,Long> {

//    @Query("select a from PET_USER a where a.id=:userId")
//    User findById(String id);

    boolean existsByUsername(String username);

    boolean existsByNickname(String username);

    Optional<User> findByUsername(String username);

}
