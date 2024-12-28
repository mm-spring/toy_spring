package com.mm.toy.api;

import com.mm.toy.Dto.UserLoginDto;
import com.mm.toy.Dto.UserRegisterDto;
import com.mm.toy.Dto.UserResponseDto;
import com.mm.toy.Dto.UserUpdateDto;
import com.mm.toy.domain.User;
import com.mm.toy.presentation.payload.dto.ApiResponseDto;
import com.mm.toy.repository.UserRepository;
import com.mm.toy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PostMapping("/signup")
    public ApiResponseDto<Long> signUp(@RequestBody UserRegisterDto userRegisterDto) {
        return ApiResponseDto.onSuccess(userService.registerUser(userRegisterDto));
    }

    @GetMapping("/login")
    public ApiResponseDto<UserLoginDto> userLogin(@RequestParam String email, @RequestParam String password) {
        User user = userService.getUserByEmailAndPassword(email, password);

        UserLoginDto userLoginDto = UserLoginDto.builder()
                .username(user.getUsername())
                .build();

        return ApiResponseDto.onSuccess(userLoginDto);
    }

    @DeleteMapping("/user")
    public ApiResponseDto<Boolean> deleteUser(@RequestParam String username) {
        userService.deleteUser(userService.getUserInfoByUsername(username).getUsername());
        return ApiResponseDto.onSuccess(true);
    }

    @PutMapping("/user/{userId}")
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
