package com.mm.toy.service;

import com.mm.toy.domain.Board;
import com.mm.toy.repository.BoardRepository;
import com.mm.toy.Dto.BoardRequestDto;
import com.mm.toy.domain.User;
import com.mm.toy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long writeBoard(String username, BoardRequestDto boardRequestDto){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User with username " + username + " not found"));

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
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User with username " + username + " not found"));
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
        return boardRepository.findByUser_Id(
                userRepository.findByUsername(username)
                        .orElseThrow(() -> new RuntimeException("User with username " + username + " not found"))
                        .getId());
    }

    @Transactional(readOnly = true)
    public Board getBoardById(Long board_id){
        return boardRepository.findById(board_id).get();
    }

    @Transactional
    public Boolean deleteBoard(Long board_id, String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User with username " + username + " not found"));

        Optional<Board> board = boardRepository.findById(board_id);

        if (!board.isPresent()) {
            return false;
        }

        if (!board.get().getUser().equals(user)){
            return false;
        }
        boardRepository.delete(board.get());
        return true;
    }

}
