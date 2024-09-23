package com.todolist.server.configs.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
// 에러 코드를 한 곳에서 관리하기 위한 열거체
public enum ErrorCode {
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "E1","올바르지 않은 입력값입니다."),
    METHOD_NOT_ARROWED(HttpStatus.METHOD_NOT_ALLOWED,"E2","잘못된 HTTP 메서드를 호출했습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"E3","서버 에러가 발생하였습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND,"E4","존재하지 않는 엔티티 입니다."),
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND,"T1","존재하지 않는 아이템 입니다.");

    private final String message;
    private final String code;
    private final HttpStatus status;

    ErrorCode(final HttpStatus status, final String code, final String message){
        this.status = status;
        this.code =code;
        this.message =message;

    }
}
