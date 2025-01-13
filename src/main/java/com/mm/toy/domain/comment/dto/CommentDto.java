package com.mm.toy.domain.comment.dto;

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
