package com.mm.toy.domain.board;


import com.mm.toy.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Board {

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long board_id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String author;

    @Column
    private String imgUrl;

}
