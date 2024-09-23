package com.todolist.server.jwt;

import com.todolist.server.entity.Member;
import com.todolist.server.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 데이터베이스에서 유저 정보 조회
        Member member = memberRepository.findByUsername(username);

        // 유저 정보 있으면, UserDetails 객체에 전달 -> 객체는 AuthenticationManager 에서 검증
        if(member != null){
            return new CustomUserDetails(member);
        }
        return null;
    }
}
