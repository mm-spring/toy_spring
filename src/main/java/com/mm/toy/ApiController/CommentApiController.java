package com.mm.toy.ApiController;

import com.mm.toy.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentApiController {
    private final CommentService commentService;


}
