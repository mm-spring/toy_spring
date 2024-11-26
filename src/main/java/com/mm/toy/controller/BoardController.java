package com.mm.toy.controller;

import com.mm.toy.Dto.BoardDto;
import com.mm.toy.Dto.BoardRequestDto;
import com.mm.toy.domain.Board;
import com.mm.toy.domain.User;
import com.mm.toy.repository.BoardRepository;
import com.mm.toy.repository.UserRepository;
import com.mm.toy.service.BoardService;
import com.mm.toy.service.CommentService;
import com.mm.toy.service.LikeService;
import com.mm.toy.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

//TODO add annotation
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class BoardController {

    //TODO Injection dependence
    private final BoardService boardService;
    private final UserService userService;
    private final BoardRepository boardRepository;
    private final LikeService likeService;
    private final UserRepository userRepository;
    private final CommentService commentService;


    /***
     * GetMapping : 게시글 리스트 조회 메서드 -> view에서 path찾기
     * @param userId
     * @param model
     * @return boardList
     */
    @GetMapping("/{userId}/boards")
    public String getBoardList(@PathVariable Long userId, Model model){
        User user = userService.getUserInfoById(userId);
        List<Board> foundBoard = boardRepository.findByUser_Id(userId);
        List<BoardDto> boards = foundBoard.stream()
                        .map(this::toDto)
                        .collect(Collectors.toList());
        model.addAttribute("userId", userId);
        model.addAttribute("boards", boards);
        return "boardList";
    }


    /**
     * GetMapping : 게시글 작성 [페이지 조회]
     * @param userId
     * @param model
     * @return ?? <- 채우기
     */
    @GetMapping("/{userId}/boards/new")
    public String showCreateBoardForm(@PathVariable Long userId, Model model) {
        //   TODO     model.addAttribute("?", new ?());
        model.addAttribute("boardDto", new BoardRequestDto());
        //   TODO     model.addAttribute("?", ?);
        model.addAttribute("userId", userId);
        return "createBoard";
    }

    /**
     * ?Mapping : 게시글 [작성]
     * @param userId
     * @param ?
     * @return
     */
    @PostMapping("/{userId}/boards/new")
    public String createBoard(@PathVariable Long userId, @ModelAttribute BoardRequestDto boardDto) {
        // TODO convert Object -> ?(특정 객체)
        User user = userService.getUserInfoById(userId);
        // TODO user 조회 -> board 작성
        boardService.writeBoard(user.getUsername(), boardDto);
        return "redirect:/user/" + userId + "/boards";
    }

    /**
     * ?Mapping : 게시글 상세 페이지 조회
     * @param userId
     * @param boardId
     * @param model
     * @return
     */
    @GetMapping("/{userId}/boards/{boardId}")
    public String getBoardDetail(@PathVariable Long userId, @PathVariable Long boardId, Model model) {
        //TODO user 조회 -> toDto 메서드 활용
        User user = userService.getUserInfoById(userId);
        BoardDto boardDto = toDto(boardService.getBoardById(boardId));

        //TODO model.addAttribute("?", ?); -> board의 데이터
        model.addAttribute("board", boardDto);
        model.addAttribute("userId", userId);
        //TODO model.addAttribute("comments", ?); -> comment List 불러오기
        model.addAttribute("comments", commentService.getCommentsByBoard(boardDto.getId()));
        //TODO model.addAttribute("?", ?); -> 해당 게시글에 좋아요를 눌렀는지에 대한 데이터 조회
        model.addAttribute("isLiked", likeService.isLiked(user.getUsername(), boardId));
        //TODO model.addAttribute("?", ?); -> 해당 게시글의 좋아요 개수
        model.addAttribute("likeCount", likeService.countLike(boardId));
        return "boardDetail";
    }

    /**
     * ?Mapping : 좋아요
     * @param ?
     * @param ?
     * @return
     */
    //TODO @?Mapping("?")
    public String likeBoard(@PathVariable Long id_1, @PathVariable Long id_2) {
        //TODO user 조회 -> like 누르기
        return "redirect:/user/" + "?" + "/boards/" + "?";
    }

    /**
     *
     * @param ?
     * @param ?
     * @return
     */
    //@?Mapping("/user/{userId}/boards/{boardId}/unlike")
    public String unlikeBoard(@PathVariable Long id_1, @PathVariable Long id_2) {
        //TODO user 조회 -> like 취소
        return "redirect:/user/" + "?" + "/boards/" + "?";
    }

    /**
     *
     * @param userId
     * @param boardId
     * @param content
     * @return
     */
    @PostMapping("/user/{userId}/boards/{boardId}/comments")
    public String addComment(@PathVariable Long userId,
                             @PathVariable Long boardId,
                             @RequestParam String content) {
        //TODO user 조회 -> comment 작성
        return "redirect:/user/" + userId + "/boards/" + boardId;
    }

    private BoardDto toDto(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writerId(board.getUser().getId())
                .commentCount(board.getComments().size())
                .likeCount(likeService.countLike(board.getId()))
                .build();
    }
}
