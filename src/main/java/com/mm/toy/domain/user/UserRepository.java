package com.mm.toy.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // save(entity), findById, findAll, delete, deleteAll, findOne
    List<User> findByName(String name);
    Optional<User> findByUsername(String username);
}
