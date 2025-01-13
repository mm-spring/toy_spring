package com.mm.toy.domain.comment.repository;

import com.mm.toy.domain.comment.entity.Comment;
import com.mm.toy.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findByBoardId(Long boardId);
    public List<Comment> findByUser(User user);
}
