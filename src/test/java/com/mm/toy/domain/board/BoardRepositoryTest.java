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
                .email("email2")
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
        Board board1 = Board.builder()
                .user(user1)
                .title("title1")
                .content("content1")
                .author("author1")
                .imgUrl("imgUrl1")
                .build();

        user1.addBoard(board1);

        // when
        boardRepository.save(board1);

        //then
        List<Board> boards = boardRepository.findAll();
        assertThat(boards.size()).isOne();
        assertThat(boards.get(0).getUser()).isEqualTo(user1);
    }

    @Test
    void updateBoard(){
        // given
        Board board1 = Board.builder()
                .user(user1)
                .title("title1")
                .content("content1")
                .author("author1")
                .imgUrl("imgUrl1")
                .build();

        user1.addBoard(board1);

        boardRepository.save(board1);

        String convertedTitle = "convertedTitle";
        String convertedContent = "convertedContent";

        // when
        board1.updateTitle(convertedTitle);
        board1.updateContent(convertedContent);

        // then
        Optional<Board> optionalBoard = boardRepository.findById(board1.getId());
        assertThat(optionalBoard).isPresent();
        assertThat(optionalBoard.get().getTitle()).isEqualTo(convertedTitle);
        assertThat(optionalBoard.get().getContent()).isEqualTo(convertedContent);
    }

    @Test
    void updateBoard2() { // DTO
        // given
        Board board1 = Board.builder()
                .user(user1)
                .title("title1")
                .content("content1")
                .author("author1")
                .imgUrl("imgUrl1")
                .build();

        user1.addBoard(board1);

        boardRepository.save(board1);

        String convertedTitle = "convertedTitle";
        String convertedContent = "convertedContent";

        // when
        BoardDto boardDTO = new BoardDto(convertedTitle, convertedContent);
        board1.update(boardDTO);

        // then
        Optional<Board> optionalBoard = boardRepository.findById(board1.getId());
        assertThat(optionalBoard).isPresent();
        assertThat(optionalBoard.get().getTitle()).isEqualTo(convertedTitle);
        assertThat(optionalBoard.get().getContent()).isEqualTo(convertedContent);

    }

    @Test
    void deleteBoard(){
        // given
        Board board1 = Board.builder()
                .user(user1)
                .title("title1")
                .content("content1")
                .author("author1")
                .imgUrl("imgUrl1")
                .build();

        user1.addBoard(board1);

        boardRepository.save(board1);

        // when
        boardRepository.deleteById(boardRepository.findAll().get(0).getId());

        //then
        List<Board> boards = boardRepository.findAll();
        assertThat(boards.size()).isZero();
    }

    @Test
    void getBoardById(){
        // given
        Board board1 = Board.builder()
                .user(user1)
                .title("title1")
                .content("content1")
                .author("author1")
                .imgUrl("imgUrl1")
                .build();
        user1.addBoard(board1);

        Board board2 = Board.builder()
                .user(user1)
                .title("title2")
                .content("content2")
                .author("author2")
                .imgUrl("imgUrl2")
                .build();
        user1.addBoard(board2);

        boardRepository.saveAll(List.of(board1, board2));

        // when
        Optional<Board> findBoard = boardRepository.findById(board1.getId());

        //then
        List<Board> boards = user1.getBoards();
        assertThat(boards.size()).isEqualTo(2);
        assertThat(boards.get(0).getTitle()).isEqualTo(findBoard.get().getTitle());
        
    }

    @Test
    void getBoardByTitle(){
        // given
        Board board1 = Board.builder()
                .user(user1)
                .title("title1")
                .content("content1")
                .author("author1")
                .imgUrl("imgUrl1")
                .build();
        user1.addBoard(board1);

        Board board2 = Board.builder()
                .user(user1)
                .title("title2")
                .content("content2")
                .author("author2")
                .imgUrl("imgUrl2")
                .build();
        user1.addBoard(board2);

        Board board3 = Board.builder()
                .user(user1)
                .title("title1")
                .content("content3")
                .author("author3")
                .imgUrl("imgUrl3")
                .build();
        user1.addBoard(board3);

        boardRepository.saveAll(List.of(board1, board2, board3));

        String givenTitle = "title1";

        // when

        //then
        List<Board> boards = boardRepository.findByTitle(givenTitle);
        assertThat(boards.size()).isEqualTo(2);

    }

    @Test
    void getBoardByUserId(){
        // given
        Board board1 = Board.builder()
                .user(user1)
                .title("title1")
                .content("content1")
                .author("author1")
                .imgUrl("imgUrl1")
                .build();
        user1.addBoard(board1);

        Board board2 = Board.builder()
                .user(user2)
                .title("title2")
                .content("content2")
                .author("author2")
                .imgUrl("imgUrl2")
                .build();
        user2.addBoard(board2);

        boardRepository.saveAll(List.of(board1, board2));

        // when
        List<Board> boards = boardRepository.findByUser_Id(user1.getId());

        //then
        assertThat(boards.size()).isOne();
        assertThat(boards.get(0).getUser()).isEqualTo(user1);

    }

    @Test
    void getBoardByUser(){
        // given
        Board board1 = Board.builder()
                .user(user1)
                .title("title1")
                .content("content1")
                .author("author1")
                .imgUrl("imgUrl1")
                .build();
        user1.addBoard(board1);

        Board board2 = Board.builder()
                .user(user1)
                .title("title2")
                .content("content2")
                .author("author2")
                .imgUrl("imgUrl2")
                .build();
        user2.addBoard(board2);

        Board board3 = Board.builder()
                .user(user1)
                .title("title3")
                .content("content3")
                .author("author3")
                .imgUrl("imgUrl3")
                .build();
        user2.addBoard(board3);

        boardRepository.saveAll(List.of(board1, board2, board3));

        // when
        List<Board> boards1 = user1.getBoards();
        List<Board> boards2 = user2.getBoards();

        // then
        assertThat(boards1.size()).isOne();
        assertThat(boards2.size()).isEqualTo(2);

    }

}