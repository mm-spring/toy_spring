package com.mm.toy.Dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequestDto {
    private String title;
    private String content;
}
