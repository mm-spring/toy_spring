package com.mm.toy.web.controller;

import ch.qos.logback.core.model.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {
    @GetMapping("/")
    public String index(Model model) {
//        model.addAttribute("posts", postsService.findAllDesc());
//        if(user != null) {
//            model.addAttribute("userName", user.getName());
//        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        return "posts-update";
    }

    @GetMapping("/posts/detail/{id}")
    public String postsDetail(@PathVariable Long id, Model model) {
//        PostsResponseDto dto = postsService.findById(id);
//        model.addAttribute("detail", dto);
//        model.addAttribute("comments", commentsService.findAllDesc(id));
        return "posts-detail";
    }
}
