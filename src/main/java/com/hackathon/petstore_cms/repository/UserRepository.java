package com.hackathon.petstore_cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hackathon.petstore_cms.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    
    User findByUsername(String username);

}