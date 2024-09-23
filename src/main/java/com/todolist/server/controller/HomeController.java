package com.todolist.server.controller;


import com.todolist.server.dto.MemberDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Iterator;

@RestController
public class HomeController {

    public HomeController(){    }
//
//    @GetMapping("/")
//    public String getUser(@RequestBody MemberDto member){
//
////        // 현재 세션 사용자 이름
////        String username = SecurityContextHolder.getContext().getAuthentication().getName();
////
////        MemberDto member = new MemberDto();
////        member.setUsername(username);
////
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////
////        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
////        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
////        GrantedAuthority auth = iter.next();
////        String role = auth.getAuthority();
//
//        return "성공";
//    }


}
