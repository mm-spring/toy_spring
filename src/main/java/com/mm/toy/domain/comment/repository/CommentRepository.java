package com.mm.toy.domain.comment.repository;

import com.mm.toy.domain.comment.entity.Comment;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Id> {
}
