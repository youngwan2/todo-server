package com.todolist.server.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// 커스텀 필터체인
@Component
public class CustomJwtRequestFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public CustomJwtRequestFilter(JWTUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String rawToken = request.getHeader("Authorization");
        String username = null, token = null;

        // 유효한 형식의 JWT 토큰이라면
        if(rawToken != null && rawToken.startsWith("Bearer")){
            token = rawToken.split(" ")[1]; // Bearer 제거
            username = jwtUtil.getUsername(token);
        }

        // 존재하는 유저라면 유저 정보를 담고 있는 객체인 userDetails 를 불러옴
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if(jwtUtil.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // IP 주소 등의 유저 부가적 정보를 token 객체에 추가
                SecurityContextHolder.getContext().setAuthentication(authToken); // 자격증명 명시
            }
        }
        filterChain.doFilter(request, response); // 다음 필터로 이동
    }
}
