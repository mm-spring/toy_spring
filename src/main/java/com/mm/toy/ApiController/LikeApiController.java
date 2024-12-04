package com.mm.toy.ApiController;

import com.mm.toy.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class LikeApiController {
    private final LikeService likeService;
}
