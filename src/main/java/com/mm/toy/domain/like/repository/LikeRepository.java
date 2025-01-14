package com.mm.toy.domain.like.repository;

import com.mm.toy.domain.board.entity.Board;
import com.mm.toy.domain.like.entity.Like;
import com.mm.toy.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    public Optional<Like> findByBoardAndUser(Board board, User user);
    public List<Like> findByBoardId(Long board_id);
    public Optional<Like> findByBoard_idAndUser_username(Long board_id, String username);
    public int countByBoardId(Long boardId);
}