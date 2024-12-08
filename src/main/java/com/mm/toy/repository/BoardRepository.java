package com.mm.toy.repository;

import com.mm.toy.domain.Board;
import com.mm.toy.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByTitle(String title);
    List<Board> findByUser_Id(Long userId);
    List<Board> findByUser(User user);
}
