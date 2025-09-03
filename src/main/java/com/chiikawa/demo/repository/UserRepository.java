package com.chiikawa.demo.repository;

import com.chiikawa.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository


public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByName(String username);
    Boolean existsByEmail(String email);
    Optional<User> findByName(String username);
}
