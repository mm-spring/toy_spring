package com.mm.toy.api;

import com.mm.toy.Dto.CommentDto;
import com.mm.toy.domain.Comment;
import com.mm.bandit.presentation.payload.dto.ApiResponseDto;
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

    @PostMapping("/{boardId}/comment")
    public ApiResponseDto<Long> writeComment(@PathVariable Long boardId,
                                            @RequestParam String username,
                                            @RequestParam String content) {
        return ApiResponseDto.onSuccess(commentService.writeComment(username, boardId, content));
    }

    @DeleteMapping("/{commentId}")
    public ApiResponseDto<Boolean> removeComment(@PathVariable Long commentId, @RequestParam String username) {
        commentService.deleteComment(username, commentId);
        return ApiResponseDto.onSuccess(true);
    }

    @PutMapping("/{commentId}")
    public ApiResponseDto<Long> editComment(@PathVariable Long commentId,
                            @RequestParam String username,
                            @RequestParam String content) {
        return ApiResponseDto.onSuccess(commentService.updateComment(username, commentId, content));
    }

    @GetMapping
    public ApiResponseDto<List<CommentDto>> getCommentsMine(@RequestParam String username){
        return ApiResponseDto.onSuccess(
                commentService.getCommentsByUser(username)
                        .stream()
                        .map(this::toCommentDto)
                        .collect(toList())
        );
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
