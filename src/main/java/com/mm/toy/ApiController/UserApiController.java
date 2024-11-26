package com.mm.toy.ApiController;

import com.mm.toy.Dto.UserLoginDto;
import com.mm.toy.Dto.UserRegisterDto;
import com.mm.toy.Dto.UserResponseDto;
import com.mm.toy.Dto.UserUpdateDto;
import com.mm.toy.domain.User;
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
    private final UserRepository userRepository;

    @PostMapping("/signup")
    public Long signUp(@RequestBody UserRegisterDto userRegisterDto) {
        return userService.registerUser(userRegisterDto);
    }

    @GetMapping("/login")
    public ResponseEntity<UserLoginDto> userLogin(@RequestParam String email, @RequestParam String password) {
        Optional<User> user = userService.getUserByEmailAndPassword(email, password);

        if (!user.isPresent()) {
            throw new RuntimeException("User not found");
        }
        UserLoginDto userLoginDto = UserLoginDto.builder()
                .username(user.get().getUsername())
                .build();

        return ResponseEntity.ok(userLoginDto);
    }

    @DeleteMapping("/user")
    public Boolean deleteUser(@RequestParam String username) {
        return userService.deleteUserById(userService.getUserInfoByUsername(username).getId());
    }

    @PutMapping("/user")
    public Long editUser(@RequestBody UserUpdateDto userUpdateDto, @RequestParam Long user_id) {
        Optional<User> user = userService.getUserInfoById(user_id);

        if (!user.isPresent()) {
            throw new RuntimeException("User not found");
        }
        return userService.updateUserInfo(userUpdateDto, user.get().getId());
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponseDto> getMemberInfoByUsername(@RequestParam String username) {
        User user = userService.getUserInfoByUsername(username);
        UserResponseDto dto = UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
        return ResponseEntity.ok(dto);

    }
}
