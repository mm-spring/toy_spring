package com.mm.toy.service;

import com.mm.toy.domain.Board;
import com.mm.toy.domain.Comment;
import com.mm.toy.domain.User;
import com.mm.bandit.presentation.payload.code.ErrorStatus;
import com.mm.bandit.presentation.payload.exception.BoardHandler;
import com.mm.bandit.presentation.payload.exception.CommentHandler;
import com.mm.bandit.presentation.payload.exception.UserHandler;
import com.mm.toy.repository.BoardRepository;
import com.mm.toy.repository.CommentRepository;
import com.mm.toy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Long writeComment(String username, Long board_id, String content){
        Board board = boardRepository.findById(board_id)
                .orElseThrow(() -> new BoardHandler(ErrorStatus.BOARD_NOT_FOUND));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        Comment comment = Comment.builder()
                .content(content)
                .board(board)
                .user(user)
                .build();

        Comment savedComment = commentRepository.save(comment);

        return savedComment.getId();
    }

    @Transactional
    public Long updateComment(String username, Long comment_id, String content){
        Comment comment = commentRepository.findById(comment_id)
                .orElseThrow(() -> new CommentHandler(ErrorStatus.COMMENT_NOT_FOUND));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        validateIsWriter(user, comment);

        comment.update(content);

        return comment.getId();
    }

    @Transactional
    public void deleteComment(String username, Long comment_id){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));
        Comment comment = commentRepository.findById(comment_id)
                .orElseThrow(() -> new CommentHandler(ErrorStatus.COMMENT_NOT_FOUND));

        validateIsWriter(user, comment);

        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public List<Comment> getCommentsByBoard(Long board_id){
        return commentRepository.findByBoardId(board_id);
    }

    @Transactional(readOnly = true)
    public List<Comment> getCommentsByUser(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        return commentRepository.findByUser(user);
    }


    private void validateIsWriter(User user, Comment comment){
        if (!comment.getUser().equals(user)){
            throw new CommentHandler(ErrorStatus.COMMENT_CAN_ONLY_BE_MODIFIED_BY_WRITER);
        }
    }

}
