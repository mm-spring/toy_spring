package com.mm.toy.domain.user.Dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto {
    private String password;
    private String email;
    private String name;
}
