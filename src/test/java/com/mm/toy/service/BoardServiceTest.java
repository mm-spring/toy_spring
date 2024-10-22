package com.mm.toy.service;

import com.mm.toy.domain.user.UserRegisterDto;
import com.mm.toy.global.service.DatabaseCleanup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        UserRegisterDto userRegisterDto1 = UserRegisterDto.builder()
                .password("password_1")
                .email("email_1")
                .name("name_1")
                .build();
        UserRegisterDto userRegisterDto2 = UserRegisterDto.builder()
                .password("password_2")
                .email("email_2")
                .name("name_2")
                .build();
        user1_id = userService.registerUser(userRegisterDto1);
        user2_id = userService.registerUser(userRegisterDto2);
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