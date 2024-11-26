package com.mm.toy.ApiController;

import com.mm.toy.Dto.BoardRequestDto;
import com.mm.toy.domain.User;
import com.mm.toy.service.BoardService;
import com.mm.toy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/boards")
public class BoardApiController {
    private final BoardService boardService;
    private final UserService userService;

    @PostMapping("/write-board")
    public Long writeBoard(@RequestParam String username, @RequestBody BoardRequestDto boardRequestDto){
        return boardService.writeBoard(username, boardRequestDto);
    }
}
