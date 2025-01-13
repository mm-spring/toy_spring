package com.mm.toy.domain.board.controller;

import com.mm.toy.domain.board.dto.BoardDto;
import com.mm.toy.domain.board.dto.BoardRequestDto;
import com.mm.toy.domain.board.dto.BoardSpecificDto;
import com.mm.toy.domain.comment.dto.CommentDto;
import com.mm.toy.domain.board.entity.Board;
import com.mm.toy.domain.comment.entity.Comment;
import com.mm.toy.presentation.payload.dto.ApiResponseDto;
import com.mm.toy.domain.board.service.BoardService;
import com.mm.toy.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/boards")
public class BoardApiController {
    private final BoardService boardService;
    private final UserService userService;

    @PostMapping
    public ApiResponseDto<Long> writeBoard(@RequestParam String username, @RequestBody BoardRequestDto boardRequestDto) {
        return ApiResponseDto.onSuccess(boardService.writeBoard(username, boardRequestDto));
    }

    @PutMapping("/{boardId}")
    public ApiResponseDto<Long> editBoard(@PathVariable Long boardId, @RequestParam String username, @RequestBody BoardRequestDto boardRequestDto) {
        return ApiResponseDto.onSuccess(boardService.editBoard(username, boardId, boardRequestDto));
    }

    @DeleteMapping("/{boardId}")
    public ApiResponseDto<Boolean> removeBoard(@PathVariable Long boardId, @RequestParam String username) {
        boardService.deleteBoard(boardId, username);
        return ApiResponseDto.onSuccess(true);
    }

    @GetMapping
    public ApiResponseDto<List<BoardDto>> getBoards(){
        return ApiResponseDto.onSuccess(
                boardService.getAllBoards()
                        .stream()
                        .map(this::toBoardDto)
                        .collect(toList())
        );
    }

    @GetMapping("/user")
    public ApiResponseDto<List<BoardDto>> getBoardsByUsername(@RequestParam String username){
        return ApiResponseDto.onSuccess
                (boardService.getBoardsByUsername(username)
                .stream()
                .map(this::toBoardDto)
                .collect(toList()));
    }

    @GetMapping("/{boardId}")
    public ApiResponseDto<BoardSpecificDto> getBoard(@PathVariable Long boardId){
        Board board = boardService.getBoardById(boardId);

        List<CommentDto> comments = board.getComments()
                .stream()
                .map(this::toCommentDto)
                .collect(toList());

        BoardSpecificDto boardSpecificDto = BoardSpecificDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writerName(board.getUser().getName())
                .likeCount(board.getLikes().size())
                .commentCount(board.getComments().size())
                .commentDto(comments)
                .build();

        return ApiResponseDto.onSuccess(boardSpecificDto);
    }

    BoardDto toBoardDto(Board board){
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

    CommentDto toCommentDto(Comment comment){
        CommentDto dto = CommentDto.builder()
                .id(comment.getId())
                .boardId(comment.getBoard().getId())
                .writerName(comment.getUser().getName())
                .content(comment.getContent())
                .build();
        return dto;
    }
}
