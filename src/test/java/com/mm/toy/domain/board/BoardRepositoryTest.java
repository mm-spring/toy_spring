package com.mm.toy.domain.board;

import com.mm.toy.domain.user.User;
import com.mm.toy.domain.user.UserRepository;
import com.mm.toy.global.service.DatabaseCleanup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
                .email("email1")
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

    @Test
    void createBoard(){
        // given
        Board board1 = new Board();
        board1.setTitle("Title1");
        board1.setAuthor("User1");
        boardRepository.save(board1);

        // when
        board1.setUser(user1);

        //then
        List<Board> boards = boardRepository.findAll();
        assertThat(boards.size()).isOne();
        assertThat(boards.get(0).getUser()).isEqualTo(user1);
    }

    @Test
    void updateBoard(){
        // given
        Board board1 = new Board();
        board1.setTitle("Title1");
        board1.setAuthor("User1");
        board1.setContent("Update1");
        boardRepository.save(board1);
        board1.setUser(user1);

        String convertedTitle = "convertedTitle";
        String convertedContent = "convertedContent";

        // when
        board1.setTitle(convertedTitle);
        board1.setContent(convertedContent);

        // then
        Optional<Board> optionalBoard = boardRepository.findById(board1.getId());
        assertThat(optionalBoard).isPresent();
        assertThat(optionalBoard.get().getTitle()).isEqualTo(convertedTitle);
        assertThat(optionalBoard.get().getContent()).isEqualTo(convertedContent);
    }

    @Test
    void deleteBoard(){
        // given
        Board board1 = new Board();
        board1.setTitle("Title1");
        board1.setAuthor("User1");
        board1.setContent("Update1");
        boardRepository.save(board1);
        board1.setUser(user1);

        // when
        Board findBoard = boardRepository.findAll().get(0);
        boardRepository.deleteById(findBoard.getId());

        //then
        List<Board> boards = boardRepository.findAll();
        assertThat(boards.size()).isZero();
    }

}