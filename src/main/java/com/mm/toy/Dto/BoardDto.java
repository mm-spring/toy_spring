package com.mm.toy.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
    private String title;
    private String content;
    private Long writerId;
    private int likeCount;
    private int commentCount;
}
