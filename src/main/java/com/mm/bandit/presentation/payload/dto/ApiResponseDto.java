package com.mm.bandit.presentation.payload.dto;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mm.bandit.presentation.payload.code.BaseCode;
import com.mm.bandit.presentation.payload.code.SuccessStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponseDto<T> {
    private final Boolean isSuccess;
    private final Integer code;
    private final String message;
    private T result;

    public static <T> ApiResponseDto<T> onSuccess(T result) {
        return new ApiResponseDto<>(true, 2000, SuccessStatus._SUCCESS.getMessage(), result);
    }

    public static <T> ApiResponseDto<T> of(BaseCode baseCode, T result){
        return new ApiResponseDto<>(true, baseCode.getReasonHttpStatus().getCode(), baseCode.getReasonHttpStatus().getMessage(), result);
    }

    public static <T> ApiResponseDto<T> onFailure(Integer code, String message, T result) {
        return new ApiResponseDto<>(false, code, message, result);
    }
}
