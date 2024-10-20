package com.mm.toy.domain.user;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {
    private String email;
    private String name;
}
