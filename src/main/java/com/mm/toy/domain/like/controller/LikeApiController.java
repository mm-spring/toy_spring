package com.mm.toy.domain.like.controller;

import com.mm.toy.domain.board.dto.BoardDto;
import com.mm.toy.domain.board.entity.Board;
import com.mm.toy.presentation.payload.dto.ApiResponseDto;
import com.mm.toy.domain.board.service.BoardService;
import com.mm.toy.domain.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeApiController {
    private final LikeService likeService;
    private final BoardService boardService;

    @PostMapping("/boards/{boardId}")
    public ApiResponseDto<Long> likeBoard(@PathVariable Long boardId, @RequestParam String username) {
        return ApiResponseDto.onSuccess(likeService.likeBoard(username, boardId));
    }

    @DeleteMapping("/boards/{boardId}")
    public ApiResponseDto<Boolean> unlikeBoard(@PathVariable Long boardId, @RequestParam String username) {
        likeService.unlikeBoard(username, boardId);
        return ApiResponseDto.onSuccess(true);
    }

    @GetMapping("/boards")
    public ApiResponseDto<List<BoardDto>> seeLikeBoards(@RequestParam String username) {
        List<Board> boards = likeService.getLikedBoardsByUsername(username);
        return ApiResponseDto.onSuccess(
                boards.stream()
                .map(this::toBoardDto)
                .toList()
        );
    }

    private BoardDto toBoardDto(Board board) {
        BoardDto dto = BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writerId(board.getUser().getId())
                .likeCount(board.getLikes().size())
                .commentCount(board.getComments().size())
                .build();

        return dto;
    }

}
