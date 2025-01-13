package com.mm.bandit.presentation.payload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseCode{
    // 서버 오류
    _INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR, 5000, "Server error. Please contact to the manager"),

    // 요청 오류
    _BAD_REQUEST(BAD_REQUEST, 4000, "Bad request"),

    // 회원 오류(4100~4199)
    USER_NOT_FOUND(NOT_FOUND, 4100, "User not found"),

    // 게시판 오류(4200~4299)
    BOARD_NOT_FOUND(NOT_FOUND, 4200, "Board not found"),
    BOARD_CAN_ONLY_BE_MODIFIED_BY_WRITER(BAD_REQUEST, 4201, "User is not authorized to write/delete this board"),

    // 댓글 오류(4300~4399)
    COMMENT_NOT_FOUND(NOT_FOUND, 4300, "Comment not found"),
    COMMENT_CAN_ONLY_BE_MODIFIED_BY_WRITER(BAD_REQUEST, 4301, "User is not authorized to write/delete this comment"),

    // 좋아요 오류(4400~4499)
    LIKE_NOT_FOUND(NOT_FOUND, 4400, "Like not found"),
    ALREADY_LIKED(BAD_REQUEST, 4401, "Already liked it"),

    // 인증 오류(4500~4599)
    AUTH_MUST_AUTHORIZED_URI(BAD_REQUEST, 4500, "인증이 필수인 uri입니다."),
    AUTH_ROLE_CANNOT_EXECUTE_URI(BAD_REQUEST, 4501, "해당 인가로는 실행할 수 없는 동작입니다."),
    AUTH_INVALID_TOKEN(BAD_REQUEST, 4502, "유효하지 않은 토큰입니다."),
    AUTH_INVALID_REFRESH_TOKEN(BAD_REQUEST, 4503, "유효하지 않은 리프레시 토큰입니다."),
    AUTH_TOKEN_HAS_EXPIRED(BAD_REQUEST, 4504, "토큰의 유효기간이 만료되었습니다."),
    AUTH_TOKEN_IS_UNSUPPORTED(BAD_REQUEST, 4504, "서버에서 지원하는 형식의 토큰이 아닙니다."),
    AUTH_IS_NULL(BAD_REQUEST, 4505, "토큰 값이 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;

    @Override
    public Reason getReason() {
        return Reason.builder()
                .isSuccess(false)
                .code(code)
                .message(message)
                .build();
    }

    @Override
    public Reason getReasonHttpStatus() {
        return Reason.builder()
                .isSuccess(false)
                .code(code)
                .message(message)
                .httpStatus(httpStatus)
                .build();
    }
}
