package com.mm.toy.service;

import com.mm.toy.domain.board.Board;
import com.mm.toy.domain.board.BoardRepository;
import com.mm.toy.domain.board.BoardRequestDto;
import com.mm.toy.domain.user.User;
import com.mm.toy.domain.user.UserRegisterDto;
import com.mm.toy.domain.user.UserRepository;
import com.mm.toy.global.service.DatabaseCleanup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
    @Autowired
    UserRepository userRepository;

    String user1_username;
    String user2_username;

    Long user1_id;
    Long user2_id;

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
        // given
        BoardRequestDto boardDto = BoardRequestDto.builder()
                .content("content")
                .title("title")
                .build();

        Long board_id = boardService.writeBoard(user1_username, boardDto);

        BoardRequestDto editBoardDto = BoardRequestDto.builder()
                .content("newContent")
                .title("newTitle")
                .build();

        // when
        Long edit_board_id = boardService.editBoard(user1_username, board_id, editBoardDto);

        // then
        Board findBoard = boardRepository.findById(edit_board_id).get();
        assertThat(findBoard.getTitle()).isEqualTo(editBoardDto.getTitle());
    }

    @Test
    void errorWhenEditBoard(){
        // given
        BoardRequestDto boardDto = BoardRequestDto.builder()
                .content("content")
                .title("title")
                .build();

        Long board_id = boardService.writeBoard(user1_username, boardDto);

        BoardRequestDto editBoardDto = BoardRequestDto.builder()
                .content("newContent")
                .title("newTitle")
                .build();

        // when
        Long edit_board_id = boardService.editBoard(user1_username, board_id, editBoardDto);

        // then
        Board findBoard = boardRepository.findById(edit_board_id).get();
        User findUser = userRepository.findById(user1_id).get();
        assertThat(findBoard.getTitle()).isEqualTo(editBoardDto.getTitle());
        assertThat(findBoard.getUser()).isEqualTo(findUser);
        //Q3-1
    }

    @Test
    void getAllBoards() {
        // given
        BoardRequestDto boardRequestDto1 = BoardRequestDto.builder()
                .title("title1")
                .content("content1")
                .build();

        BoardRequestDto boardRequestDto2 = BoardRequestDto.builder()
                .title("title2")
                .content("content2")
                .build();

        boardService.writeBoard(user1_username, boardRequestDto1);
        boardService.writeBoard(user2_username, boardRequestDto2);

        // when
        List<Board> boardList = boardService.getAllBoards();

        // then
        assertThat(boardList.size()).isEqualTo(2);
    }

    @Test
    void getBoardsByUsername() {
        // given
        BoardRequestDto boardRequestDto1 = BoardRequestDto.builder()
                .title("title1")
                .content("content1")
                .build();

        BoardRequestDto boardRequestDto2 = BoardRequestDto.builder()
                .title("title2")
                .content("content2")
                .build();

        boardService.writeBoard(user1_username, boardRequestDto1);
        boardService.writeBoard(user2_username, boardRequestDto2);

        // when
        List<Board> findBoard = boardService.getBoardsByUsername(user1_username);

        // then
        assertThat(findBoard.get(0).getTitle()).isEqualTo(boardRequestDto1.getTitle());
    }

    @Test
    void getBoardById() {
        // given
        BoardRequestDto boardRequestDto1 = BoardRequestDto.builder()
                .title("title1")
                .content("content1")
                .build();

        BoardRequestDto boardRequestDto2 = BoardRequestDto.builder()
                .title("title2")
                .content("content2")
                .build();

        Long board_id1 = boardService.writeBoard(user1_username, boardRequestDto1);
        Long board_id2 = boardService.writeBoard(user2_username, boardRequestDto2);

        // when
        Board findBoard = boardService.getBoardById(board_id1);

        // then
        assertThat(findBoard.getId()).isEqualTo(board_id1);
    }
}