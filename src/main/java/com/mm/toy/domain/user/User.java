package com.mm.toy.domain.user;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Table(name = "users")      //test시 h2 데이터베이스 사용하는데, 해당 데이터베이스에는 user 명칭 사용 안됨
@Entity
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

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

}
