package com.mm.toy.domain.user;

import com.mm.toy.global.service.DatabaseCleanup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    DatabaseCleanup databaseCleanup;

    @AfterEach
    void cleanUp() {
        databaseCleanup.truncateAllEntity();
    }

    @Test
    @DisplayName("유저를 생성합니다")
    public void createUser() {
        //given
        User user1 = User.builder()
                .email("email")
                .role("role")
                .name("name")
                .build();
        //when
        userRepository.save(user1);
        //then
        List<User> all = userRepository.findAll();
        assertThat(all.size()).isOne();
    }
    @Test
    @DisplayName("유저를 생성 뒤 수정합니다.")
    public void updateUser() {

    }

    @Test
    @DisplayName("유저를 생성 뒤 삭제합니다.")
    public void deleteUser() {

    }

    @Test
    @DisplayName("유저를 생성 뒤 아이디를 통해 특정 유저만을 조회합니다.")
    public void getUserById() {

    }

    @Test
    @DisplayName("유저를 생성 뒤 이름을 통해 유저 리스트를 조회합니다.")
    public void getUsersByName() {

    }

}