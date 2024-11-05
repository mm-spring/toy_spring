package com.mm.toy.controller;

import com.mm.toy.Dto.UserRegisterDto;
import com.mm.toy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;

    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("userRegisterDto", new UserRegisterDto());
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(@ModelAttribute UserRegisterDto userRegisterDto) {
        userService.registerUser(userRegisterDto);
        return "redirect:/";
    }

}
