package com.todolist.server.configs.error.exception;

import com.todolist.server.configs.error.ErrorCode;

public class NotFoundException extends BusinessBaseException{

    public NotFoundException(ErrorCode errorCode){
        super(errorCode.getMessage(), errorCode);
    }

    public NotFoundException(){
        super(ErrorCode.NOT_FOUND);
    }
}
