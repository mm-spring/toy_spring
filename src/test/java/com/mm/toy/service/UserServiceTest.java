package com.mm.toy.service;

import com.mm.toy.domain.User;
import com.mm.toy.Dto.UserRegisterDto;
import com.mm.toy.repository.UserRepository;
import com.mm.toy.Dto.UserUpdateDto;
import com.mm.toy.global.service.DatabaseCleanup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class UserServiceTest {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DatabaseCleanup databaseCleanup;

    @AfterEach
    void cleanUp() {
        databaseCleanup.truncateAllEntity();
    }

    @Test
    void registerUser() {
        // given
        UserRegisterDto userRegisterDto = UserRegisterDto.builder()
                .password("password")
                .email("email")
                .name("name")
                .build();

        // when
        Long user_id = userService.registerUser(userRegisterDto);

        // then
        Optional<User> user = userRepository.findById(user_id);
        assertThat(user).isPresent();
        assertThat(user.get().getEmail()).isEqualTo(userRegisterDto.getEmail());
        assertThat(user.get().getUsername()).isNotNull();
        assertThat(user.get().getUsername().length()).isGreaterThanOrEqualTo((6));
    }

    @Test
    void updateUserInfo() {
        // given
        UserRegisterDto registerDto = UserRegisterDto.builder()
                .password("password")
                .email("email")
                .name("name")
                .build();

        Long user_id = userService.registerUser(registerDto);

        UserUpdateDto updateDto = UserUpdateDto.builder()
                .email("newEmail")
                .name("newName")
                .build();

        // when
        Long update_user_id = userService.updateUserInfo(updateDto, user_id);

        // then
        Optional<User> user = userRepository.findById(user_id);
        assertThat(user).isPresent();
        assertThat(user.get().getEmail()).isEqualTo(updateDto.getEmail());
        assertThat(user.get().getName()).isEqualTo(updateDto.getName());
    }

    @Test
    void notUpdateUserInfo(){
        // given
        UserRegisterDto registerDto = UserRegisterDto.builder()
                .password("password")
                .email("email")
                .name("name")
                .build();

        Long user_id = userService.registerUser(registerDto);
        UserUpdateDto updateDto = UserUpdateDto.builder()
                .email("newEmail")
                .name("newName")
                .build();

        Optional<User> optionalUser = userRepository.findById(user_id);

        // when
        optionalUser.get().updateUserInfo(updateDto);

        // then
        User findUser = userRepository.findById(user_id).get();
        assertThat(findUser.getEmail()).isNotEqualTo(optionalUser.get().getEmail());
        userRepository.save(optionalUser.get());
        userRepository.flush();
        User findUser2 = userRepository.findById(user_id).get();
        assertThat(findUser2.getEmail()).isEqualTo(optionalUser.get().getEmail());
        //Q1-1, Q1-2
    }

    @Test
    void getAllUser() {
        // given
        UserRegisterDto userRegisterDto1 = UserRegisterDto.builder()
                .name("name1")
                .password("password1")
                .email("email1")
                .build();

        UserRegisterDto userRegisterDto2 = UserRegisterDto.builder()
                .name("name2")
                .password("password2")
                .email("email2")
                .build();

        Long user1_id = userService.registerUser(userRegisterDto1);
        Long user2_id = userService.registerUser(userRegisterDto2);

        // when
        List<User> userList = userService.getAllUser();

        // then
        assertThat(userList.size()).isEqualTo(2);
    }

    @Test
    void getUserInfoById() {
        // given
        UserRegisterDto userRegisterDto1 = UserRegisterDto.builder()
                .name("name1")
                .password("password1")
                .email("email1")
                .build();

        UserRegisterDto userRegisterDto2 = UserRegisterDto.builder()
                .name("name2")
                .password("password2")
                .email("email2")
                .build();

        Long user1_id = userService.registerUser(userRegisterDto1);
        Long user2_id = userService.registerUser(userRegisterDto2);

        // when
        User findUser = userService.getUserInfoById(user1_id).get();

        // then
        assertThat(findUser.getEmail()).isEqualTo(userRegisterDto1.getEmail());
    }

    @Test
    @Transactional
    void getUserInfoByUsername() {
        // given
        UserRegisterDto userRegisterDto = UserRegisterDto.builder()
                .name("name")
                .password("password")
                .email("email")
                .build();

        Long user_id = userService.registerUser(userRegisterDto);
        Optional<User> optionalUser = userRepository.findById(user_id);

        // when
        User findUser = userService.getUserInfoByUsername(optionalUser.get().getUsername());

        // then
        assertThat(findUser.getUsername()).isEqualTo(optionalUser.get().getUsername());
        assertThat(findUser).isEqualTo(optionalUser.get());
    }
}