package com.mm.toy.domain.user;

import com.mm.toy.global.service.DatabaseCleanup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    @Transactional
    @DisplayName("유저를 생성 뒤 수정합니다.")
    public void updateUser() {
        //given
        User user1 = User.builder()
                .email("email")
                .role("role")
                .name("name")
                .password("password")
                .build();
        User save = userRepository.save(user1);
        String updatePassword = "updatePassword";
        //when
        save.updatePassword(updatePassword);
        //then
        User findUser = userRepository.findAll().get(0);
        assertThat(findUser.getPassword()).isEqualTo(updatePassword);
    }

    @Test
    @DisplayName("유저를 생성 뒤 삭제합니다.")
    public void deleteUser() {
        //given
        User user1 = User.builder()
                .email("email")
                .role("role")
                .name("name")
                .password("password")
                .build();
        User save = userRepository.save(user1);
        //when
        userRepository.delete(save);
        //then
        assertThat(userRepository.findAll().size()).isZero();
    }

    @Test
    @DisplayName("유저를 생성 뒤 아이디를 통해 특정 유저만을 조회합니다.")
    public void getUserById() {
        //given
        User user1 = User.builder()
                .email("email1")
                .role("role1")
                .name("name1")
                .password("password1")
                .build();
        User user2 = User.builder()
                .email("email2")
                .role("role2")
                .name("name2")
                .password("password2")
                .build();
        List<User> users = userRepository.saveAll(List.of(user1, user2));

        //when
        Optional<User> byId = userRepository.findById(users.get(0).getId());
        //then
        assertThat(byId).isPresent();
        assertThat(byId.get().getName()).isEqualTo(user1.getName());
    }

    @Test
    @DisplayName("유저를 생성 뒤 이름을 통해 유저 리스트를 조회합니다.")
    public void getUsersByName() {
        //given
        User user1 = User.builder()
                .email("email1")
                .role("role1")
                .name("name1")
                .password("password1")
                .build();
        User user2 = User.builder()
                .email("email2")
                .role("role2")
                .name("name2")
                .password("password2")
                .build();
        User user3 = User.builder()
                .email("email3")
                .role("role3")
                .name("name1")
                .password("password3")
                .build();
        List<User> users = userRepository.saveAll(List.of(user1, user2, user3));
        //when
        List<User> findUsers = userRepository.findByName(users.get(0).getName());
        //then
        assertThat(findUsers.size()).isEqualTo(2);
        assertThat(findUsers).anyMatch(user -> user.getPassword().equals(user1.getPassword()));
        assertThat(findUsers).anyMatch(user -> user.getPassword().equals(user3.getPassword()));
    }

}