package com.mm.toy.api;

import com.mm.toy.Dto.BoardDto;
import com.mm.toy.Dto.BoardRequestDto;
import com.mm.toy.Dto.BoardSpecificDto;
import com.mm.toy.Dto.CommentDto;
import com.mm.toy.domain.Board;
import com.mm.toy.domain.Comment;
import com.mm.toy.service.BoardService;
import com.mm.toy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/boards")
public class BoardApiController {
    private final BoardService boardService;
    private final UserService userService;

    @PostMapping("/board")
    public Long writeBoard(@RequestParam String username, @RequestBody BoardRequestDto boardRequestDto) {
        return boardService.writeBoard(username, boardRequestDto);
    }

    //TODO edit mapping method
    @PostMapping("/board/{boardId}")
    public Long editBoard(@PathVariable Long boardId, @RequestParam String username, @RequestBody BoardRequestDto boardRequestDto) {
        return boardService.editBoard(username, boardId, boardRequestDto);
    }

    @DeleteMapping("/board/{boardId}")
    public Boolean removeBoard(@PathVariable Long boardId, @RequestParam String username) {
        return boardService.deleteBoard(boardId, username);
    }

    @GetMapping("/board")
    public List<BoardDto> getBoards(){
        return boardService.getAllBoards()
                .stream()
                .map(this::toBoardDto)
                .collect(toList());
    }

    @GetMapping("/board/user")
    public List<BoardDto> getBoardsByUsername(@RequestParam String username){
        return boardService.getBoardsByUsername(username)
                .stream()
                .map(this::toBoardDto)
                .collect(toList());
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<BoardSpecificDto> getBoard(@PathVariable Long boardId){
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

        return ResponseEntity.ok(boardSpecificDto);
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
