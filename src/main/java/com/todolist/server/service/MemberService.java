package com.todolist.server.service;

import com.todolist.server.dto.MemberDto;
import com.todolist.server.entity.Member;
import com.todolist.server.exception.InvalidException;
import com.todolist.server.exception.UserAlreadyExistException;
import com.todolist.server.repository.MemberRepository;
import com.todolist.server.utils.Validator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberService(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // 유저 중복 체크
    public void findByUser(String username){
        Member member = memberRepository.findByUsername(username);
        if(member !=null){
            throw new UserAlreadyExistException("이미 존재하는 유저");
        }
    }

    // 유저 등록
    public void registerUser(MemberDto memberDto) {
        Validator validator = new Validator();

        String email = memberDto.getEmail();
        String password = memberDto.getPassword();
        String username = memberDto.getUsername();
        String role = "ROLE_USER";

        // 이미 존재하는 유저 인지 확인
        Boolean isExist = memberRepository.existsByUsername(username);

        if (isExist) {
            throw new UserAlreadyExistException("이미 존재하는 유저");
        }

        try {
            // 유효성 검사
            validator.EmailValidator(email);
            validator.PasswordValidator(password);
            validator.UsernameValidator(username);

            // 유저 정보를 담고 있는 객체 생성
            Member member = new Member();
            member.setEmail(email);
            member.setPassword(bCryptPasswordEncoder.encode(password));
            member.setUsername(username);
            member.setRole(role);

            // 데이터베이스에 유저정보 저장
            memberRepository.save(member);

        } catch (Exception ex) {
            throw new InvalidException(ex.getMessage());
        }
    }
    // id 로 유저 정보 조회
    public Member findUserInfoByUsername(String username){
        return memberRepository.findByUsername(username);
    }
}
