package com.mm.toy.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // save(entity), findById, findAll, delete, deleteAll, findOne
}
