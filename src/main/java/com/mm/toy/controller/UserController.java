package com.mm.toy.controller;

import com.mm.toy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;

    @PostMapping("/user/register")
    public String userRegister() {

    }

    @GetMapping("/user/{username}")
    public String getUserInfo() {

    }

    @GetMapping("/user")
    public String getUsersInfo() {

    }

}
