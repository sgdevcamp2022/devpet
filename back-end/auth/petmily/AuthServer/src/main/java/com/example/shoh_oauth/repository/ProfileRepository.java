package com.example.shoh_oauth.repository;

import com.example.shoh_oauth.data.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Profile findByUsername(String username);

}