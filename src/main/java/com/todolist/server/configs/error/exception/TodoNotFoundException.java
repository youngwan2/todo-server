package com.todolist.server.configs.error.exception;

import com.todolist.server.configs.error.ErrorCode;

public class TodoNotFoundException extends NotFoundException {

    public TodoNotFoundException(){
        super(ErrorCode.ITEM_NOT_FOUND);
    }
}
