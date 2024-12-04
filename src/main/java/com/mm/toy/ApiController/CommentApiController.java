package com.mm.toy.ApiController;

import com.mm.toy.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentApiController {
    private final CommentService commentService;

    @PostMapping("/boards/{boardId}/comment")
    public Long writeComment(@PathVariable Long boardId, @RequestParam String username, @RequestParam String content) {
        return commentService.writeComment(username, boardId, content);
    }

    @DeleteMapping("comment/{commentId}")
    public Boolean removeComment(@PathVariable Long commentId, @RequestParam String username) {
        try{
            commentService.deleteComment(username, commentId);
        }
        catch(Exception e){
            return false;
        }
        return true;
    }

    @PostMapping("comment/{commentId}")
    public Long editComment(@PathVariable Long commentId, @RequestParam String username, @RequestParam String content) {
        return commentService.updateComment(username, commentId, content);
    }
}
