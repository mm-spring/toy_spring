package com.mm.toy.ApiController;

import com.mm.toy.Dto.UserRegisterDto;
import com.mm.toy.domain.User;
import com.mm.toy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PostMapping("signup")
    public Long signUp(@RequestBody UserRegisterDto userRegisterDto) {
        return userService.registerUser(userRegisterDto);
    }
}
