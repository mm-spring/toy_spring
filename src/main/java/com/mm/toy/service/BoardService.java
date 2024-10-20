package com.mm.toy.service;

import com.mm.toy.domain.board.Board;
import com.mm.toy.domain.board.BoardRepository;
import com.mm.toy.domain.board.BoardRequestDto;
import com.mm.toy.domain.user.User;
import com.mm.toy.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private BoardRepository boardRepository;
    private UserService userService;

    @Transactional
    public Long writeBoard(String username, BoardRequestDto boardRequestDto){
        Board board = Board.builder()
                .title(boardRequestDto.getTitle())
                .content(boardRequestDto.getContent())
                .build();
        Board savedBoard = boardRepository.save(board);
        User user = userService.getUserInfoByUsername(username);
        user.addBoard(savedBoard);
        return savedBoard.getId();
    }

    @Transactional
    public Long editBoard(String username, Long board_id, BoardRequestDto boardRequestDto){
        User user = userService.getUserInfoByUsername(username);
        Board board = boardRepository.findById(board_id).get();
        if (board.getUser() == user){
            board.update(boardRequestDto);
        }
    }

}
