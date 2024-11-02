package com.mm.toy.domain.board.Entity;


import com.mm.toy.domain.board.Dto.BoardRequestDto;
import com.mm.toy.domain.comment.entity.Comment;
import com.mm.toy.domain.like.entity.Like;
import com.mm.toy.domain.user.Entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "boards")
public class Board {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private List<Like> likes = new ArrayList<>();

    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private String author;

    private String imgUrl;

    public void updateTitle(String updateTitle){
        this.title = updateTitle;
    }

    public void updateContent(String updateContent){
        this.content = updateContent;
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
    }

}
