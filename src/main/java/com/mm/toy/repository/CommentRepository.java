package com.mm.toy.repository;

import com.mm.toy.domain.Comment;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Id> {
}
