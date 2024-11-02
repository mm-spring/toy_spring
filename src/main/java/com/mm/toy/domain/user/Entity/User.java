package com.mm.toy.domain.user.Entity;

import com.mm.toy.domain.board.Entity.Board;
import com.mm.toy.domain.comment.entity.Comment;
import com.mm.toy.domain.like.entity.Like;
import com.mm.toy.domain.user.Dto.UserUpdateDto;
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

    @Column(unique = true)
    private String username;

    private String password;

    private String email;

    private String role;

    private String name;

    public void updatePassword(String updatePassword){
        this.password = updatePassword;
    }

    public void addBoard(Board board){
        this.boards.add(board);
    }

    public void updateUserInfo(UserUpdateDto userUpdateDto){
        this.email = userUpdateDto.getEmail();
        this.name = userUpdateDto.getName();
    }

}
