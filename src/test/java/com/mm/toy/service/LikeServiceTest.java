package com.mm.toy.service;

import com.mm.toy.Dto.BoardRequestDto;
import com.mm.toy.Dto.UserRegisterDto;
import com.mm.toy.domain.Like;
import com.mm.toy.global.service.DatabaseCleanup;
import com.mm.toy.repository.BoardRepository;
import com.mm.toy.repository.LikeRepository;
import com.mm.toy.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Disabled
@SpringBootTest
class LikeServiceTest{
    @Autowired
    UserService userService;
    @Autowired
    BoardService boardService;
    @Autowired
    LikeService likeService;
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DatabaseCleanup databaseCleanup;

    String user1_username;
    String user2_username;
    String user3_username;
    String user4_username;

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
        UserRegisterDto userDto3 = UserRegisterDto.builder()
                .password("password_3")
                .email("email_3")
                .name("name_3")
                .build();
        UserRegisterDto userDto4 = UserRegisterDto.builder()
                .password("password_4")
                .email("email_4")
                .name("name_4")
                .build();
        Long user1_id = userService.registerUser(userDto1);
        Long user2_id = userService.registerUser(userDto2);
        Long user3_id = userService.registerUser(userDto3);
        Long user4_id = userService.registerUser(userDto4);

        user1_username = userService.getUserInfoById(user1_id).getUsername();
        user2_username = userService.getUserInfoById(user2_id).getUsername();
        user3_username = userService.getUserInfoById(user3_id).getUsername();
        user4_username = userService.getUserInfoById(user4_id).getUsername();


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
    @Transactional
    void likeBoard(){
        // when
        Long like_id = likeService.likeBoard(user3_username, board1_id);

        // then
        Optional<Like> optionalLike = likeRepository.findByBoardAndUser(boardRepository.findById(board1_id).get(), userRepository.findByUsername(user3_username).get());
        assertThat(optionalLike).isPresent();
        assertThat(optionalLike.get().getUser().getUsername()).isEqualTo(user3_username);
        assertThat(optionalLike.get().getBoard().getUser().getUsername()).isEqualTo(user1_username);
    }

    @Test
    void alreadyLikeIt(){
        // given
        Long like_id = likeService.likeBoard(user3_username, board1_id);

        // when & then
        assertThatThrownBy(() -> likeService.likeBoard(user3_username, board1_id))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Already liked it");

    }

    @Test
    @Transactional
    void unlikeBoard(){
        // given
        likeService.likeBoard(user3_username, board1_id);
        likeService.likeBoard(user2_username, board1_id);

        // when
        likeService.unlikeBoard(user3_username, board1_id);

        // then
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList.size()).isOne();
        assertThat(likeList.get(0).getUser().getUsername()).isEqualTo(user2_username);
    }

    @Test
    void cannotUnlikeBoardBecauseNotExist(){
        // given
        likeService.likeBoard(user3_username, board1_id);

        // when & then
        assertThatThrownBy(() -> likeService.unlikeBoard(user2_username, board1_id))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Like not found");
        assertThatThrownBy(() -> likeService.unlikeBoard(user3_username, board2_id))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Like not found");
    }

    @Test
    void countLike(){
        // given
        likeService.likeBoard(user3_username, board1_id);
        likeService.likeBoard(user2_username, board1_id);
        likeService.likeBoard(user1_username, board1_id);

        // when
        int like_count = likeService.countLike(board1_id);

        // then
        assertThat(like_count).isEqualTo(3);
    }

    @Test
    void isLiked(){
        // given
        likeService.likeBoard(user3_username, board1_id);
        likeService.likeBoard(user2_username, board1_id);

        // when
        Boolean isLiked_1 = likeService.isLiked(user3_username, board1_id);
        Boolean isLiked_2 = likeService.isLiked(user2_username, board1_id);
        Boolean isLiked_3 = likeService.isLiked(user1_username, board1_id);

        // then
        assertThat(isLiked_1).isTrue();
        assertThat(isLiked_2).isTrue();
        assertThat(isLiked_3).isFalse();
    }





}
