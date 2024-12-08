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
        }//TODO Exception은 범위가 방대함. 어느 예외에서 catch 할것인지 특정 필요
        catch(RuntimeException e){
            return false;
        }
        return true;
    }

    //TODO edit method of mapping
    @PostMapping("comment/{commentId}")
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
