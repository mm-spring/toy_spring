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
    public String getBoardList(@PathVariable Long userId, Model model) {
        User user = userService.getUserInfoById(userId);
        List<Board> boards = boardService.getAllBoards();
        List<BoardDto> collect = boards.stream().map(board -> toDto(board)).collect(Collectors.toList());
        model.addAttribute("boards", collect);
        model.addAttribute("userId", userId);
        return "boardList";
    }

    @GetMapping("/user/{userId}/boards/new")
    public String showCreateBoardForm(@PathVariable Long userId, Model model) {
        model.addAttribute("boardDto", new BoardRequestDto());
        model.addAttribute("userId", userId);
        return "createBoard";
    }

    @PostMapping("/user/{userId}/boards/new")
    public String createBoard(@PathVariable Long userId, @ModelAttribute BoardRequestDto boardDto) {
        User user = userService.getUserInfoById(userId);
        boardService.writeBoard(user.getUsername(), boardDto);
        return "redirect:/user/" + userId + "/boards";
    }

    @GetMapping("/user/{userId}/boards/{boardId}")
    public String getBoardDetail(@PathVariable Long userId, @PathVariable Long boardId, Model model) {
        User user = userService.getUserInfoById(userId);
        BoardDto dto = toDto(boardService.getBoardById(boardId));

        model.addAttribute("board", dto);
        model.addAttribute("userId", userId);
        model.addAttribute("comments", commentService.getCommentsByBoard(dto.getId()));
        model.addAttribute("isLiked", likeService.isLiked(user.getUsername(), boardId));
        model.addAttribute("likeCount", likeService.countLike(boardId));
        return "boardDetail";
    }

    @PostMapping("/user/{userId}/boards/{boardId}/like")
    public String likeBoard(@PathVariable Long userId, @PathVariable Long boardId) {
        User user = userService.getUserInfoById(userId);
        likeService.likeBoard(user.getUsername(), boardId);
        return "redirect:/user/" + userId + "/boards/" + boardId;
    }

    @PostMapping("/user/{userId}/boards/{boardId}/unlike")
    public String unlikeBoard(@PathVariable Long userId, @PathVariable Long boardId) {
        User user = userService.getUserInfoById(userId);
        likeService.unlikeBoard(user.getUsername(), boardId);
        return "redirect:/user/" + userId + "/boards/" + boardId;
    }

    @PostMapping("/user/{userId}/boards/{boardId}/comments")
    public String addComment(@PathVariable Long userId,
                             @PathVariable Long boardId,
                             @RequestParam String content) {
        User user = userService.getUserInfoById(userId);
        commentService.writeComment(user.getUsername(), boardId, content);
        return "redirect:/user/" + userId + "/boards/" + boardId;
    }

    private BoardDto toDto(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writerId(board.getUser().getId())
                .commentCount(board.getComments().size())
                .likeCount(likeService.countLike(board.getId()))
                .build();
    }
}
