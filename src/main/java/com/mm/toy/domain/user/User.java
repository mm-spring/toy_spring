package com.mm.toy.domain.user;

import com.mm.toy.domain.board.Board;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user")
    private List<Board> boards = new ArrayList<>();

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String role;

    @Column
    private String name;

    void updatePassword(String updatePassword){
        this.password = updatePassword;
    }

}
