package com.mm.toy.ApiController;

import com.mm.toy.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class LikeApiController {
    private final LikeService likeService;

    @PostMapping("boards/{boardId}")
    public Long likeBoard(@PathVariable Long boardId, @RequestParam String username) {
        return likeService.likeBoard(username, boardId);
    }
}
