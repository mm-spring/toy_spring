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

import java.util.List;

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
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User with username " + username + " not found"));
        return boardRepository.findByUser(user);
    }

    @Transactional(readOnly = true)
    public Board getBoardById(Long board_id){
        return boardRepository.findById(board_id)
                .orElseThrow(() -> new RuntimeException("Board with id " + board_id + " not found"));
    }

    @Transactional
    public void deleteBoard(Long board_id, String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User with username " + username + " not found"));

        Board board = boardRepository.findById(board_id)
                .orElseThrow(() -> new RuntimeException("Board with id " + board_id + " not found"));;

        // 검증
        isUserSameWithBoardUser(board, user);

        // delete
        List<Comment> comments = commentRepository.findByBoardId(board_id);
        commentRepository.deleteAll(comments);

        List<Like> likes = likeRepository.findByBoardId(board_id);
        likeRepository.deleteAll(likes);

        boardRepository.delete(board);
    }

    private void isUserSameWithBoardUser(Board board, User user){
        if (!board.getUser().equals(user)){
            throw new RuntimeException("User is not authorized to delete this board");
        }
    }

    private Board toBoard(BoardRequestDto boardRequestDto, User user){

    }

}
