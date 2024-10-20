package com.mm.toy.domain.user;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String password;
    private String email;
    private String name;
}
