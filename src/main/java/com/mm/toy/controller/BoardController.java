package com.mm.toy.controller;

import com.mm.toy.service.BoardService;
import com.mm.toy.service.CommentService;
import com.mm.toy.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardService boardService;
    private final LikeService likeService;
    private final CommentService commentService;

    @GetMapping("/boards")
    public String getBoardList() {

    }

    @GetMapping("/boards/{boardId}")
    public String getBoardInfo() {

    }

    @PostMapping("/boards/write")
    public String writeBoard() {

    }

    @PutMapping("/boards/edit/{boardId}")
    public String editBoard() {

    }

    @DeleteMapping("/boards/delete/{boardId}")
    public String deleteBoard() {

    }
}
