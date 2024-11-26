package com.mm.toy.ApiController;

import com.mm.toy.Dto.BoardDto;
import com.mm.toy.Dto.BoardRequestDto;
import com.mm.toy.domain.Board;
import com.mm.toy.domain.User;
import com.mm.toy.service.BoardService;
import com.mm.toy.service.UserService;
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

    @PostMapping("/board")
    public Long writeBoard(@RequestParam String username, @RequestBody BoardRequestDto boardRequestDto) {
        return boardService.writeBoard(username, boardRequestDto);
    }

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
                .map(this::toDto)
                .collect(toList());
    }

    BoardDto toDto(Board board){
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
