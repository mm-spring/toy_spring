package com.mm.toy.domain;


import com.mm.toy.Dto.BoardRequestDto;
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

    @Builder.Default
    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    @Builder.Default
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

    public void addComment(Comment comment){ this.comments.add(comment); }

    public void addLike(Like like){this.likes.add(like);}
}
