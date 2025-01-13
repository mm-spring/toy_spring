package com.mm.toy.presentation.payload.exception;

import com.mm.toy.presentation.payload.code.BaseCode;
import com.mm.toy.presentation.payload.code.Reason;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {
    private BaseCode baseCode;

    @Override
    public String getMessage() {
        return baseCode.getReason().getMessage();
    }

    public Reason getErrorReason(){
        return baseCode.getReason();
    }

    public Reason getErrorReasonHttpStatus(){
        return baseCode.getReasonHttpStatus();
    }
}
