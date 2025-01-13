package com.mm.toy.domain.board.dto;

import com.mm.toy.domain.comment.dto.CommentDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardSpecificDto {
    Long id;
    String title;
    String content;
    String writerName;
    int likeCount;
    int commentCount;
    List<CommentDto> commentDto;
}
