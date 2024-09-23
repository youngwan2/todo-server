package com.todolist.server.dto;

import lombok.RequiredArgsConstructor;

public class JwtResponseDto {

    private final String token;

    public JwtResponseDto(String token){
        this.token = token;
    }

    public String getToken(){
        return this.token;
    }
}
