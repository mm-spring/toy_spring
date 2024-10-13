package com.mm.toy.domain.board;

import com.mm.toy.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByTitle(String title);
    List<Board> findByUser_Id(Long userId);
}
