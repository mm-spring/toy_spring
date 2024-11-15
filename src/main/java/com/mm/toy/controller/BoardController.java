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

//TODO add annotation
public class BoardController {

    //TODO Injection dependence


    /***
     * GetMapping : 게시글 리스트 조회 메서드 -> view에서 path찾기
     * @Argument userId, model
     * @return boardList
     */


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
