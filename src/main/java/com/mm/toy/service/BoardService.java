package com.mm.toy.service;

import com.mm.toy.domain.board.Entity.Board;
import com.mm.toy.domain.board.Repository.BoardRepository;
import com.mm.toy.domain.board.Dto.BoardRequestDto;
import com.mm.toy.domain.user.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserService userService;

    @Transactional
    public Long writeBoard(String username, BoardRequestDto boardRequestDto){
        User user = userService.getUserInfoByUsername(username);

        Board board = Board.builder()
                .title(boardRequestDto.getTitle())
                .content(boardRequestDto.getContent())
                .user(user)
                .build();

        Board savedBoard = boardRepository.save(board);

        user.addBoard(savedBoard);
        return savedBoard.getId();
    }

    @Transactional
    public Long editBoard(String username, Long board_id, BoardRequestDto boardRequestDto){
        User user = userService.getUserInfoByUsername(username);
        Board board = boardRepository.findById(board_id).get();

        if (board.getUser().equals(user)) {
            board.update(boardRequestDto);
        }

        return board.getId();
    }

    @Transactional(readOnly = true)
    public List<Board> getAllBoards(){
        return boardRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Board> getBoardsByUsername(String username){
        return boardRepository.findByUser_Id(userService.getUserInfoByUsername(username).getId());
    }

    @Transactional(readOnly = true)
    public Board getBoardById(Long board_id){
        return boardRepository.findById(board_id).get();
    }



}
