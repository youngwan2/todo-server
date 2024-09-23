package com.todolist.server.configs.error.exception;

import com.todolist.server.configs.error.ErrorCode;

// 비즈니스 로직 작성 시 발생하는 예외를 모아두는 최상위 클래스
public class BusinessBaseException  extends RuntimeException {

    private final ErrorCode errorCode;

    // 사용자 정의 메시지와 에러 코드 설정
    public BusinessBaseException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }

    // 기본 설정 메시지와 에러 코드 설정
    public BusinessBaseException(ErrorCode code){
        super(code.getMessage());
        this.errorCode = code;
    }

    // 에러 코드 반환
    public ErrorCode getErrorCode(){
        return errorCode;
    }
}