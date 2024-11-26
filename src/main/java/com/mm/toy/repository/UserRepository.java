package com.mm.toy.repository;

import com.mm.toy.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // save(entity), findById, findAll, delete, deleteAll, findOne
    List<User> findByName(String name);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmailAndPassword(String email, String password);
}
