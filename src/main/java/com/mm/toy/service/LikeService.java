package com.mm.toy.service;

import com.mm.toy.domain.Board;
import com.mm.toy.domain.Like;
import com.mm.toy.domain.User;
import com.mm.toy.repository.BoardRepository;
import com.mm.toy.repository.LikeRepository;
import com.mm.toy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public Long likeBoard(String username, Long board_id) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Board board = boardRepository.findById(board_id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        if (isLikePresent(board, user)) {
            throw new RuntimeException("Already liked it");
        }

        Like like = Like.builder()
                .board(board)
                .user(user)
                .build();

        Like savedLike = likeRepository.save(like);
        // user.addLike(like);
        // board.addLike(like);

        return savedLike.getId();
    }

    @Transactional
    public void unlikeBoard(String username, Long board_id) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Board board = boardRepository.findById(board_id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        Optional<Like> foundLike = likeRepository.findByBoardAndUser(board, user);

        if (!foundLike.isPresent()) {
            throw new RuntimeException("Like not found");

        }
        likeRepository.delete(foundLike.get());
    }

    @Transactional(readOnly = true)
    public int countLike(Long board_id) {
        return likeRepository.countByBoardId(board_id);
    }

    @Transactional(readOnly = true)
    public Boolean isLiked(String username, Long board_id){
        return likeRepository.findByBoard_idAndUser_username(board_id, username).isPresent();
    }

    public Boolean isLikePresent(Board board, User user){
        Optional<Like> foundLike = likeRepository.findByBoardAndUser(board, user);
        return foundLike.isPresent();
    }
}
