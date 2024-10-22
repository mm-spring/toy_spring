package com.mm.toy.service;

import com.mm.toy.domain.board.Board;
import com.mm.toy.domain.board.BoardRepository;
import com.mm.toy.domain.board.BoardRequestDto;
import com.mm.toy.domain.user.UserRegisterDto;
import com.mm.toy.global.service.DatabaseCleanup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    BoardService boardService;
    @Autowired
    DatabaseCleanup databaseCleanup;
    @Autowired
    BoardRepository boardRepository;

    String user1_username;
    String user2_username;

    @BeforeEach
    void setUp(){
        UserRegisterDto userDto1 = UserRegisterDto.builder()
                .password("password_1")
                .email("email_1")
                .name("name_1")
                .build();
        UserRegisterDto userDto2 = UserRegisterDto.builder()
                .password("password_2")
                .email("email_2")
                .name("name_2")
                .build();
        Long user1_id = userService.registerUser(userDto1);
        Long user2_id = userService.registerUser(userDto2);

        user1_username = userService.getUserInfoById(user1_id).getUsername();
        user2_username = userService.getUserInfoById(user2_id).getUsername();
    }

    @AfterEach
    void cleanUp() {
        databaseCleanup.truncateAllEntity();
    }

    @Test
    void writeBoard() {
        // given
        BoardRequestDto boardDto = BoardRequestDto.builder()
                .title("title")
                .content("content")
                .build();

        // when
        Long board_id = boardService.writeBoard(user1_username, boardDto);

        // then
        Board findBoard = boardRepository.findById(board_id).get();
        assertThat(findBoard.getContent()).isEqualTo(boardDto.getContent());
    }

    @Test
    void errorWhenWriteBoard(){
        // given
        BoardRequestDto boardDto = BoardRequestDto.builder()
                .title("title")
                .content("content")
                .build();

        // when
        Long board_id = boardService.writeBoard(user1_username, boardDto);

        // then
        Board findBoard = boardRepository.findById(board_id).get();
        //findBoard.getUser().isEqualTo(user);
        //Q2-1
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