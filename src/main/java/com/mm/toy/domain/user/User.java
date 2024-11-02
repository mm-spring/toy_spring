package com.mm.toy.domain.user;

import com.mm.toy.domain.board.Board;
import com.mm.toy.domain.comment.Comment;
import com.mm.toy.domain.like.Like;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Like> likes = new ArrayList<>();

    private String username;

    private String password;

    private String email;

    private String role;

    private String name;

    void updatePassword(String updatePassword){
        this.password = updatePassword;
    }

    public void addBoard(Board board){
        this.boards.add(board);
    }

}
