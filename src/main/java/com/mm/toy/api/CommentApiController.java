package com.mm.toy.api;

import com.mm.toy.Dto.CommentDto;
import com.mm.toy.domain.Comment;
import com.mm.toy.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentApiController {
    private final CommentService commentService;

    @PostMapping("/boards/{boardId}/comment")
    public Long writeComment(@PathVariable Long boardId,
                             @RequestParam String username,
                             @RequestParam String content) {
        return commentService.writeComment(username, boardId, content);
    }

    @DeleteMapping("comment/{commentId}")
    public Boolean removeComment(@PathVariable Long commentId, @RequestParam String username) {
        try{
            commentService.deleteComment(username, commentId);
        }
        catch(RuntimeException e){
            return false;
        }
        return true;
    }

    @PutMapping("comment/{commentId}")
    public Long editComment(@PathVariable Long commentId,
                            @RequestParam String username,
                            @RequestParam String content) {
        return commentService.updateComment(username, commentId, content);
    }

    @GetMapping("comment")
    public List<CommentDto> getCommentsMine(@RequestParam String username){
        return commentService.getCommentsByUser(username)
                .stream()
                .map(this::toCommentDto)
                .collect(toList());
    }

    private CommentDto toCommentDto(Comment comment){
        CommentDto commentDto = CommentDto.builder()
                .id(comment.getId())
                .boardId(comment.getBoard().getId())
                .writerName(comment.getUser().getUsername())
                .content(comment.getContent())
                .build();

        return commentDto;
    }
}
