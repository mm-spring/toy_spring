package com.mm.toy.api;

import com.mm.toy.Dto.BoardDto;
import com.mm.toy.domain.Board;
import com.mm.toy.service.BoardService;
import com.mm.toy.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class LikeApiController {
    private final LikeService likeService;
    private final BoardService boardService;

    @PostMapping("boards/{boardId}")
    public Long likeBoard(@PathVariable Long boardId, @RequestParam String username) {
        return likeService.likeBoard(username, boardId);
    }

    @DeleteMapping("boards/{boardId}")
    public Boolean unlikeBoard(@PathVariable Long boardId, @RequestParam String username) {
        try{
            likeService.unlikeBoard(username, boardId);
        }
        catch(RuntimeException e){
            return false;
        }
        return true;
    }

    @GetMapping("boards")
    public List<BoardDto> seeLikeBoards(@RequestParam String username) {
        List<Board> boards = likeService.getLikedBoardsByUsername(username);
        return boards.stream()
                .map(this::toBoardDto)
                .toList();
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
