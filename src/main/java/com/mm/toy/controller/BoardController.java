package com.mm.toy.controller;

import com.mm.toy.Dto.BoardDto;
import com.mm.toy.domain.Board;
import com.mm.toy.domain.User;
import com.mm.toy.service.BoardService;
import com.mm.toy.service.CommentService;
import com.mm.toy.service.LikeService;
import com.mm.toy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class BoardController {
    private final UserService userService;
    private final BoardService boardService;
    private final LikeService likeService;
    private final CommentService commentService;

    @GetMapping("/user/{userId}/boards")
    public String getBoardList(@PathVariable Long userId, Model model) {
        User user = userService.getUserInfoById(userId);
        List<Board> boards = boardService.getBoardsByUsername(user.getUsername());
        List<BoardDto> collect = boards.stream().map(board -> toDto(board)).collect(Collectors.toList());
        model.addAttribute("boards", collect);
        return "boardList";
    }

    private BoardDto toDto(Board board) {
        return BoardDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .commentCount(board.getComments().size())
                .likeCount(likeService.countLike(board.getId()))
                .build();
    }
}
