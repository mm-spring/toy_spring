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

    /**
     * 1. signUp페이지를 불러오는 조회 메서드
     * 2. 유저가 signUp하여 서버에게 post하는 메서드
     *
     * 둘의 path는 signUp으로 동일하다.
     */

}
