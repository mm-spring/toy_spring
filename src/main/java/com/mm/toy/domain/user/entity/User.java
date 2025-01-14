package com.mm.toy.domain.user.entity;

import com.mm.toy.domain.user.dto.UserUpdateDto;
import com.mm.toy.domain.like.entity.Like;
import com.mm.toy.domain.board.entity.Board;
import com.mm.toy.domain.comment.entity.Comment;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Board> boards = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Like> likes = new ArrayList<>();

    @Column(unique = true)
    private String username;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String name;

    // OAuth2
    private String provider; // 어떤 OAuth인지
    private String provideId; // 해당 OAuth의 ID

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role.getKey()));
    }

}
