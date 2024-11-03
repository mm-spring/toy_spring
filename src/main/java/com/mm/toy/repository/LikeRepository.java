package com.mm.toy.repository;

import com.mm.toy.domain.Board;
import com.mm.toy.domain.Like;
import com.mm.toy.domain.User;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    public Optional<Like> findByBoardAndUser(Board board, User user);
}
