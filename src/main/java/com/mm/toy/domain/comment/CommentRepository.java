package com.mm.toy.domain.comment;

import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Id> {
}
