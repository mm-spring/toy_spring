package com.mm.toy.domain.board;

import com.mm.toy.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
