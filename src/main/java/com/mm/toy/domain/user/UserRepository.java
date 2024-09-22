package com.mm.toy.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    // save(entity), findById, findAll, delete, deleteAll, findOne
}
