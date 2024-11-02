package com.mm.toy.domain;

import com.mm.toy.Dto.UserUpdateDto;
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

    public void addComment(Comment comment){ this.comments.add(comment); }

    public void addLike(Like like){this.likes.add(like);}

}

