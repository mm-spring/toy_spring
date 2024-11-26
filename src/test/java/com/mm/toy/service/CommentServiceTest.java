package com.mm.toy.service;

import com.mm.toy.Dto.BoardRequestDto;
import com.mm.toy.Dto.UserRegisterDto;
import com.mm.toy.domain.Board;
import com.mm.toy.domain.Comment;
import com.mm.toy.repository.CommentRepository;
import com.mm.toy.global.service.DatabaseCleanup;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Slf4j
@SpringBootTest
class CommentServiceTest{
    @Autowired
    UserService userService;
    @Autowired
    BoardService boardService;
    @Autowired
    CommentService commentService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    DatabaseCleanup databaseCleanup;

    String user1_username;
    String user2_username;

    Long board1_id;
    Long board2_id;
    Long board3_id;

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

        user1_username = userService.getUserInfoById(user1_id).get().getUsername();
        user2_username = userService.getUserInfoById(user2_id).get().getUsername();

        BoardRequestDto boardDto1 = BoardRequestDto.builder()
                .content("content1")
                .title("title")
                .build();

        BoardRequestDto boardDto2 = BoardRequestDto.builder()
                .content("content2")
                .title("title")
                .build();

        BoardRequestDto boardDto3 = BoardRequestDto.builder()
                .content("content3")
                .title("different title")
                .build();

        board1_id = boardService.writeBoard(user1_username, boardDto1);
        board2_id = boardService.writeBoard(user1_username, boardDto2);
        board3_id = boardService.writeBoard(user2_username, boardDto3);
    }

    @AfterEach
    void cleanUp() {
        databaseCleanup.truncateAllEntity();
    }

    @Test
    void writeComment(){
        // given
        String content = "Content";

        // when
        Long comment_id = commentService.writeComment(user2_username, board1_id, content);

        // then
        List<Comment> comments = commentRepository.findAll();
        assertThat(comments.size()).isOne();
        Comment comment = comments.get(0);
        assertThat(comment.getBoard().getId()).isEqualTo(board1_id);
        assertThat(comment.getUser().getUsername()).isEqualTo(user2_username);
    }

    @Test
    void notFoundBoardWhenWriteComment(){
        // given
        String content = "Content";

        // when & then
        assertThatThrownBy(() -> commentService.writeComment(user1_username, 1000L, content))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Board not found");
    }

    @Test
    void notFoundWriterWhenWriteComment(){
        // given
        String content = "Content";

        // when & then
        assertThatThrownBy(() -> commentService.writeComment("username", board1_id, content))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User not found");
    }

    @Test
    @Transactional
    void questionOfTransactional_1(){
        // given
        String content = "Content";

        // when
        Long comment_id = commentService.writeComment(user2_username, board1_id, content);

        // then
        Board findBoard = boardService.getBoardById(board1_id);
        assertThat(findBoard.getComments().size()).isEqualTo(1);
    }

    @Test
    void updateComment(){
        // given
        String content = "Content";
        Long comment_id = commentService.writeComment(user2_username, board1_id, content);
        String editContent = "EditedContent";

        // when
        commentService.updateComment(user2_username, board1_id, editContent);

        // then
        Comment comment = commentRepository.findById(comment_id).get();
        assertThat(comment.getContent()).isEqualTo(editContent);
    }

    @Test
    void notWriterWhenUpdateComment(){
        // given
        String content = "Content";
        Long comment_id = commentService.writeComment(user2_username, board1_id, content);
        String editContent = "EditedContent";

        //when & then
        assertThatThrownBy(() -> commentService.updateComment(user1_username, board1_id, content))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Only writer can edit");
    }

    @Test
    void deleteComment(){
        // given
        String content = "Content";
        Long comment_id = commentService.writeComment(user2_username, board1_id, content);

        // when
        commentService.deleteComment(user2_username, comment_id);

        // then
        List<Comment> comments = commentRepository.findAll();
        assertThat(comments.size()).isZero();
    }

    @Test
    void notWriterWhenDeleteComment(){
        // given
        String content = "Content";
        Long comment_id = commentService.writeComment(user2_username, board1_id, content);

        // when & then
        assertThatThrownBy(() -> commentService.deleteComment(user1_username, comment_id))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Only writer can edit");
    }

    @Test
    @Transactional
    void getCommentsByBoard(){
        // given
        commentService.writeComment(user1_username, board1_id, "Content1");
        commentService.writeComment(user2_username, board1_id, "Content2");
        commentService.writeComment(user2_username, board1_id, "Content3");

        // when
        List<Comment> comments = commentService.getCommentsByBoard(board1_id);

        // then
        assertThat(comments.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    void questionOfTransactional_2(){
        // given
        commentService.writeComment(user1_username, board1_id, "Content1");
        commentService.writeComment(user2_username, board1_id, "Content2");
        commentService.writeComment(user2_username, board1_id, "Content3");

        // when
        List<Comment> comments = commentService.getCommentsByBoard(board1_id);

        // then
        Board board = boardService.getBoardById(board1_id);
        // log.info("comments = {}", comments.size());
        // log.info("board.getComments = {}", board.getComments().size());
        assertThat(board.getComments().size()).isNotEqualTo(comments.size());
    }

    @Test
    void getCommentsByUser(){
        // given
        commentService.writeComment(user1_username, board1_id, "Content1");
        commentService.writeComment(user2_username, board1_id, "Content2");
        commentService.writeComment(user2_username, board1_id, "Content3");

        // when
        List<Comment> user2_comments = commentService.getCommentsByUser(user2_username);

        // then
        assertThat(user2_comments.size()).isEqualTo(2);
    }
}
