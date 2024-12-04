package com.mm.toy.ApiController;

import com.mm.toy.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentApiController {
    private final CommentService commentService;

    @PostMapping("comment/{boardId}")
    public Long writeComment(@PathVariable Long boardId, @RequestParam String username, @RequestParam String content) {
        return commentService.writeComment(username, boardId, content);
    }
}
