package com.mm.toy.domain.board;

import com.mm.toy.domain.user.User;
import com.mm.toy.domain.user.UserRepository;
import com.mm.toy.global.service.DatabaseCleanup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DatabaseCleanup databaseCleanup;

    User user1;
    User user2;

    @BeforeEach
    void setUp(){
        user1 = User.builder()
                .username("username1")
                .password("password1")
                .email("eamil1")
                .name("name1")
                .build();

        user2 = User.builder()
                .username("username2")
                .password("password2")
                .email("eamil2")
                .name("name2")
                .build();
        userRepository.saveAll(List.of(user1, user2));
    }

    @AfterEach
    void cleanUp() {
        databaseCleanup.truncateAllEntity();
    }

}