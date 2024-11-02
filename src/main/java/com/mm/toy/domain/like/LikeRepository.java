package com.mm.toy.domain.like;

import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Id> {
}
