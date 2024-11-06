package com.mm.toy.service;

import com.mm.toy.domain.Board;
import com.mm.toy.domain.Comment;
import com.mm.toy.domain.User;
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
                .orElseThrow(() -> new RuntimeException("Board not found"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = Comment.builder()
                .content(content)
                .board(board)
                .user(user)
                .build();

        Comment savedComment = commentRepository.save(comment);
        // board.addComment(comment);
        // user.addComment(comment);

        return savedComment.getId();
    }

    @Transactional
    public Long updateComment(String username, Long comment_id, String content){
        Comment comment = commentRepository.findById(comment_id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!comment.getUser().equals(user)){
            throw new RuntimeException("Only writer can edit");
        }
        comment.update(content);

        return comment.getId();
    }

    @Transactional
    public void deleteComment(String username, Long comment_id){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Comment comment = commentRepository.findById(comment_id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        if (comment.getUser().equals(user)){
            commentRepository.delete(comment);
        }
        else{
            throw new RuntimeException("Only writer can edit");
        }

    }

    @Transactional(readOnly = true)
    public List<Comment> getCommentsByBoard(Long board_id){
        return commentRepository.findByBoardId(board_id);
    }

    @Transactional(readOnly = true)
    public List<Comment> getCommentsByUser(String username){
        return commentRepository.findByUser(userRepository.findByUsername(username).get());
    }

}
