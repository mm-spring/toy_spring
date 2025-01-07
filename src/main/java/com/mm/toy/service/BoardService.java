package com.mm.toy.service;

import com.mm.toy.domain.Board;
import com.mm.toy.domain.Comment;
import com.mm.toy.domain.Like;
import com.mm.toy.presentation.payload.code.ErrorStatus;
import com.mm.toy.presentation.payload.exception.BoardHandler;
import com.mm.toy.presentation.payload.exception.UserHandler;
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
                .orElseThrow(() -> new UserHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Board board = toBoard(boardRequestDto, user);

        Board savedBoard = boardRepository.save(board);

        user.addBoard(savedBoard);
        return savedBoard.getId();
    }

    @Transactional
    public Long editBoard(String username, Long board_id, BoardRequestDto boardRequestDto){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Board board = boardRepository.findById(board_id)
                .orElseThrow(() -> new BoardHandler(ErrorStatus.BOARD_NOT_FOUND));

        // 검증
        validateIsWriter(user, board);

        board.update(boardRequestDto);

        return board.getId();
    }

    @Transactional(readOnly = true)
    public List<Board> getAllBoards(){
        return boardRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Board> getBoardsByUsername(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserHandler(ErrorStatus.MEMBER_NOT_FOUND));
        return boardRepository.findByUser(user);
    }

    @Transactional(readOnly = true)
    public Board getBoardById(Long board_id){
        return boardRepository.findById(board_id)
                .orElseThrow(() -> new BoardHandler(ErrorStatus.BOARD_NOT_FOUND));
    }

    @Transactional
    public void deleteBoard(Long board_id, String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Board board = boardRepository.findById(board_id)
                .orElseThrow(() -> new BoardHandler(ErrorStatus.BOARD_NOT_FOUND));

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
            throw new BoardHandler(ErrorStatus.BOARD_CAN_ONLY_BE_MODIFIED_BY_WRITER);
        }
    }

    private Board toBoard(BoardRequestDto boardRequestDto, User user){
        Board board = Board.builder()
                .title(boardRequestDto.getTitle())
                .content(boardRequestDto.getContent())
                .user(user)
                .build();

        return board;
    }


    private void validateIsWriter(User user, Board board){
        if (!board.getUser().equals(user)) {
            throw new BoardHandler(ErrorStatus.BOARD_CAN_ONLY_BE_MODIFIED_BY_WRITER);
        }
    }

}
