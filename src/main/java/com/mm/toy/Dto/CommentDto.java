package com.mm.toy.Dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    Long id;
    Long boardId;
    String writerName;
    String content;
}
