package com.mm.toy.domain.user;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String password;
    private String email;
    private String name;
}
