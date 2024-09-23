package com.todolist.server.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto {
    private String username;
    private String accessToken;
    private String role;

}
