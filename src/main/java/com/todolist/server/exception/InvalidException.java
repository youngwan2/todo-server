package com.todolist.server.exception;

public class InvalidException extends RuntimeException {

    public InvalidException(String message){
        super(message);
    }
}
