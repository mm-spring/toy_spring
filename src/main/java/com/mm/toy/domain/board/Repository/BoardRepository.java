package com.mm.toy.domain.board.Repository;

import com.mm.toy.domain.board.Entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByTitle(String title);
    List<Board> findByUser_Id(Long userId);
}
