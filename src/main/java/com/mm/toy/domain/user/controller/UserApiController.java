package com.mm.toy.domain.user.controller;

import com.mm.toy.domain.user.dto.UserLoginDto;
import com.mm.toy.domain.user.dto.UserRegisterDto;
import com.mm.toy.domain.user.dto.UserResponseDto;
import com.mm.toy.domain.user.dto.UserUpdateDto;
import com.mm.toy.domain.user.entity.User;
import com.mm.toy.presentation.payload.dto.ApiResponseDto;
import com.mm.toy.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PostMapping
    public ApiResponseDto<Long> signUp(@RequestBody UserRegisterDto userRegisterDto) {
        return ApiResponseDto.onSuccess(userService.registerUser(userRegisterDto));
    }

    @GetMapping
    public ApiResponseDto<UserLoginDto> userLogin(@RequestParam String email, @RequestParam String password) {
        User user = userService.getUserByEmailAndPassword(email, password);

        UserLoginDto userLoginDto = UserLoginDto.builder()
                .username(user.getUsername())
                .build();

        return ApiResponseDto.onSuccess(userLoginDto);
    }

    @DeleteMapping
    public ApiResponseDto<Boolean> deleteUser(@RequestParam String username) {
        userService.deleteUser(userService.getUserInfoByUsername(username).getUsername());
        return ApiResponseDto.onSuccess(true);
    }

    @PutMapping("/{userId}")
    public ApiResponseDto<Long> editUser(@RequestBody UserUpdateDto userUpdateDto, @PathVariable Long uesrId) {
        User user = userService.getUserInfoById(uesrId);

        return ApiResponseDto.onSuccess(userService.updateUserInfo(userUpdateDto, user.getId()));
    }

    @GetMapping("/user")
    public ApiResponseDto<UserResponseDto> getMemberInfoByUsername(@RequestParam String username) {
        User user = userService.getUserInfoByUsername(username);
        UserResponseDto dto = UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
        return ApiResponseDto.onSuccess(dto);
    }
}
