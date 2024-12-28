package com.mm.toy.presentation.payload.exception;

import com.mm.toy.presentation.payload.code.BaseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;


public class BoardHandler extends GeneralException {
    public BoardHandler(BaseCode baseCode) {
        super(baseCode);
    }
}
