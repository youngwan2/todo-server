package com.todolist.server.configs.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 인자 없는 생성자 -> 동일 패키지 및 상위 클래스에서 접근 가능
public class ErrorResponse {

    private String message;
    private String code;

    private ErrorResponse(final ErrorCode code){
        this.message = code.getMessage();
        this.code = code.getCode();
    }

    private ErrorResponse(final ErrorCode code, final String message){
        this.message = message;
        this.code = code.getCode();
    }

    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }

    public static ErrorResponse of(final ErrorCode code, final String message){
        return new ErrorResponse(code, message);
    }
}
