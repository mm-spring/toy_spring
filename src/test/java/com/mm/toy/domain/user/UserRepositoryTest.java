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
        //TODO setter 명칭이 아닌 user 내 메서드를 사용해서 password 변경하기
        user1.updatePassword(updatePassword);
        //then
        User findUser = userRepository.findAll().get(0);
        assertThat(findUser.getPassword()).isEqualTo(updatePassword);
    }

    @Test
    @DisplayName("유저를 생성 뒤 삭제합니다.")
    public void deleteUser() {
        //TODO 위 create와 update을 참고하여 완성하기
        // given: create user(개수 1)
        User user1 = User.builder()
                .email("email")
                .role("role")
                .name("name")
                .password("password")
                .build();
        userRepository.save(user1);
        // when: delete user
        userRepository.delete(user1);
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
        //TODO id를 통해 Optional user를 가져온다. 이때 변수명은 byId
        Optional<User> byId = userRepository.findById(user1.getId());
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
        //TODO name을 통해 list user를 가져온다. 이때 argu는 users의 첫번째 객체의 네임으로 사용한다. 가져온 변수명은 findUsers
        List<User> findUsers = userRepository.findByName(users.get(0).getName());
        //then
        assertThat(findUsers.size()).isEqualTo(2);
        assertThat(findUsers).anyMatch(user -> user.getPassword().equals(user1.getPassword()));
        assertThat(findUsers).anyMatch(user -> user.getPassword().equals(user3.getPassword()));
    }

}