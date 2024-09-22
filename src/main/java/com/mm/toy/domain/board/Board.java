package com.mm.toy.domain.board;


import com.mm.toy.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
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

}
