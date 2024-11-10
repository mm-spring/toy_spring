package com.mm.toy.controller;

import com.mm.toy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final UserService userService;
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("users", userService.getAllUser());
        return "home";
    }
}
