package com.chiikawa.demo.repository;

import com.chiikawa.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository


public interface UserRepository extends JpaRepository<User, Long> {
}
