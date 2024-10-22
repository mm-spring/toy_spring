package com.mm.toy.service;

import com.mm.toy.domain.user.User;
import com.mm.toy.domain.user.UserDto;
import com.mm.toy.domain.user.UserRepository;
import com.mm.toy.global.service.DatabaseCleanup;
import org.hibernate.dialect.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
        UserDto userDto = UserDto.builder()
                .password("password")
                .email("email")
                .name("name")
                .build();

        // when
        Long user_id = userService.registerUser(userDto);

        // then
        Optional<User> user = userRepository.findById(user_id);
        assertThat(user).isPresent();
        assertThat(user.get().getEmail()).isEqualTo(userDto.getEmail());
        assertThat(user.get().getUsername()).isNotNull();
        assertThat(user.get().getUsername()).isGreaterThanOrEqualTo(String.valueOf(6));
    }

    @Test
    void updateUserInfo() {
    }

    @Test
    void getAllUser() {
    }

    @Test
    void getUserInfoById() {
    }

    @Test
    void getUserInfoByUsername() {
    }
}