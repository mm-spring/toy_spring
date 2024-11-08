package com.mm.toy.controller;

import com.mm.toy.Dto.BoardDto;
import com.mm.toy.Dto.BoardRequestDto;
import com.mm.toy.domain.Board;
import com.mm.toy.domain.User;
import com.mm.toy.service.BoardService;
import com.mm.toy.service.CommentService;
import com.mm.toy.service.LikeService;
import com.mm.toy.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BoardController {
    private final UserService userService;
    private final BoardService boardService;
    private final LikeService likeService;
    private final CommentService commentService;

    @GetMapping("/user/{userId}/boards")
    public String getBoardList(@PathVariable String userId, Model model) {
        User user = userService.getUserInfoById(Long.valueOf(userId));
        List<Board> boards = boardService.getBoardsByUsername(user.getUsername());
        List<BoardDto> collect = boards.stream().map(board -> toDto(board)).collect(Collectors.toList());
        model.addAttribute("boards", collect);
        model.addAttribute("userId", userId);
        return "boardList";
    }

    @GetMapping("/user/{userId}/boards/new")
    public String showCreateBoardForm(@PathVariable String userId, Model model) {
        model.addAttribute("boardDto", new BoardRequestDto());
        model.addAttribute("userId", userId);
        return "createBoard";
    }

    @PostMapping("/user/{userId}/boards/new")
    public String createBoard(@PathVariable String userId, @ModelAttribute BoardRequestDto boardDto) {
        log.info("userId = {}", userId);
        User user = userService.getUserInfoById(Long.valueOf(userId));
        boardService.writeBoard(user.getUsername(), boardDto);
        return "redirect:/user/" + userId + "/boards";
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
