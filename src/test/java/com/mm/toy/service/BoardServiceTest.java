package com.mm.toy.service;

import com.mm.toy.domain.user.UserDto;
import com.mm.toy.global.service.DatabaseCleanup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    BoardService boardService;
    @Autowired
    DatabaseCleanup databaseCleanup;

    Long user1_id;
    Long user2_id;

    @BeforeEach
    void setUp(){
        UserDto userDto1 = UserDto.builder()
                .password("password_1")
                .email("email_1")
                .name("name_1")
                .build();
        UserDto userDto2 = UserDto.builder()
                .password("password_2")
                .email("email_2")
                .name("name_2")
                .build();
        user1_id = userService.registerUser(userDto1);
        user2_id = userService.registerUser(userDto2);
    }

    @AfterEach
    void cleanUp() {
        databaseCleanup.truncateAllEntity();
    }

    @Test
    void writeBoard() {
    }

    @Test
    void editBoard() {
    }

    @Test
    void getAllBoards() {
    }

    @Test
    void getBoardsByUsername() {
    }

    @Test
    void getBoardById() {
    }
}