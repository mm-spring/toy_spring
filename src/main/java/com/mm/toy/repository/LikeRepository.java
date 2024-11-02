package com.mm.toy.repository;

import com.mm.toy.domain.Like;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Id> {
}
