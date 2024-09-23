package com.todolist.server.controller;

import com.todolist.server.dto.MemberDto;
import com.todolist.server.entity.Member;
import com.todolist.server.jwt.CustomUserDetailsService;
import com.todolist.server.jwt.JWTUtil;
import com.todolist.server.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MemberController {
    private final MemberService memberService;
    private final CustomUserDetailsService customUserDetailsService;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public MemberController(MemberService memberService, CustomUserDetailsService customUserDetailsService, JWTUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.memberService = memberService;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<?> addMember(@RequestBody MemberDto member) {
        memberService.registerUser(member);
        Map<String, String> response = new HashMap<>();
        response.put("message", "회원가입 승인");
        response.put("success","true");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);


    }

//    // 회원 중복 체크
//    @PostMapping("/duplicate-check")
//    public ResponseEntity<?> duplicateCheck(@RequestBody String username){
//
//
//    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDto member) throws Exception {
        try {
            // 인증 토큰 생성
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword());
            // 인증 토큰 기반 사용자 자격증명 생성
            authenticationManager.authenticate(authToken);
        } catch (BadCredentialsException ex) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Incorrect Username and Password");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(member.getUsername());

        if (userDetails != null) {
            final String jwt = jwtUtil.createToken(member.getUsername(), "USER", 1000000000000L);

            Map<String, String> response = new HashMap<>();
            response.put("token", jwt);
            response.put("message","로그인 승인");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        // 응답
        Map<String, String> response = new HashMap<>();
        response.put("message", "네트워크 문제로 인한 로그인 실패");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    //    유저 전용 페이지
    @GetMapping("/authentication")
    public ResponseEntity<?> user(Authentication auth) {

        Member loginMember = memberService.findUserInfoByUsername(auth.getName());

        Map<String, String> response = new HashMap<>();
        response.put("message", "인증 성공");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

