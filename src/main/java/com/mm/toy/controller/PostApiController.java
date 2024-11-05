package com.mm.toy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostApiController {
    @PostMapping
    public Long save() {
//        return postsService.save(image, title, content, author);
        return 0L;
    }

    @PutMapping("/{id}")
    public Long update(@PathVariable Long id) {
//        return postsService.update(id, requestDto);
        return 0L;
    }

    @GetMapping("/{id}")
    public void findById(@PathVariable Long id) {
//        return postsService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

    }

    @GetMapping
    public List<String> getPostsList(){
        return List.of("hi");
    }
}
