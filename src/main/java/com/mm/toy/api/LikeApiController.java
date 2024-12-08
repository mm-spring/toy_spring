package com.mm.toy.api;

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
    //TODO 내가 좋아요 한 게시글 조회하기 메서드 추가
    @GetMapping("boards")
    public List<Board> seeLikeBoards(@RequestParam String username) {

    }

}
