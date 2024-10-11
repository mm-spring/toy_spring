package com.mm.toy.domain.board;


import com.mm.toy.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

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

    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private String author;

    private String imgUrl;

    void updateTitle(String updateTitle){
        this.title = updateTitle;
    }

    void updateContent(String updateContent){
        this.content = updateContent;
    }

    void updateTitleAndContent(String updateTitle, String updateContent){
        this.title = updateTitle;
        this.content = updateContent;
    }

}
