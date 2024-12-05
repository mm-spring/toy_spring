package com.mm.toy.service;

import com.mm.toy.domain.Board;
import com.mm.toy.domain.Comment;
import com.mm.toy.domain.Like;
import com.mm.toy.repository.BoardRepository;
import com.mm.toy.Dto.BoardRequestDto;
import com.mm.toy.domain.User;
import com.mm.toy.repository.CommentRepository;
import com.mm.toy.repository.LikeRepository;
import com.mm.toy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public Long writeBoard(String username, BoardRequestDto boardRequestDto){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User with username " + username + " not found"));

        //TODO 아래 builder메서드를 private 메서드 한줄로 변경
        /**
         * method name : of
         * return : Board
         * arguments : dto, user
         */
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
        //TODO get()메서드 사용 x
        Board board = boardRepository.findById(board_id).get();

        //TODO 아래 64-66과정을 private 메서드 한줄로 변경
        /**
         * method name : validateIsWriter
         * return : void
         * arguments : user, board
         */
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
        //TODO repository메서드 사용은 boardRepository.findBy~ 하나만 사용
        return boardRepository.findByUser_Id(
                userRepository.findByUsername(username)
                        .orElseThrow(() -> new RuntimeException("User with username " + username + " not found"))
                        .getId());
    }

    @Transactional(readOnly = true)
    public Board getBoardById(Long board_id){
        //TODO get()메서드 사용 x
        return boardRepository.findById(board_id).get();
    }

    @Transactional
    public Boolean deleteBoard(Long board_id, String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User with username " + username + " not found"));

        //TODO board가 존재하지 않을 때 예외 발생
        Optional<Board> board = boardRepository.findById(board_id);

        if (!board.isPresent()) {
            return false;
        }

        //TODO 아래 검증 과정을 private method 한줄로 변경
        //미리 만들었던 메서드 이용
        if (!board.get().getUser().equals(user)){
            return false;
        }

        // delete
        List<Comment> comments = commentRepository.findByBoardId(board_id);
        commentRepository.deleteAll(comments);

        List<Like> likes = likeRepository.findByBoardId(board_id);
        likeRepository.deleteAll(likes);

        boardRepository.delete(board.get());
        return true;
    }

}
