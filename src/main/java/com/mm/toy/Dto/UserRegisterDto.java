package com.mm.toy.Dto;

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
