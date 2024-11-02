package com.mm.toy.domain.like.repository;

import com.mm.toy.domain.like.entity.Like;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Id> {
}
