package com.mm.toy.presentation.payload.exception;

import com.mm.toy.presentation.payload.code.BaseCode;

public class UserHandler extends GeneralException {
    public UserHandler(BaseCode baseCode) {
        super(baseCode);
    }
}
