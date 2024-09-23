package com.todolist.server.configs;

import com.todolist.server.jwt.CustomJwtRequestFilter;
import com.todolist.server.jwt.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // 웹 보안 기능 활성화
public class SecurityConfig {

    // 요청이 들어올 때마다 JWT 토큰을 검사하는 필터
    private final CustomJwtRequestFilter customJwtRequestFilter;

    private AuthenticationManager authenticationManager;

    // 사용자의 정보를 가져오는 서비스: 일반적으로 사용자의 로그인 정보를 데이터베이스나 다른 저장소에서 로드
    private CustomUserDetailsService customUserDetailsService;

    // SecurityConfig 생성자
    public SecurityConfig(CustomJwtRequestFilter customJwtRequestFilter) {
        this.customJwtRequestFilter = customJwtRequestFilter;

    }

    // 인증 매니저를 전역적으로 사용하기 위해 Bean 으로 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // 이제 해당 메서드를 어느 클래스에서든 주입할 수 있음
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);// CSRF disable

        http.formLogin(AbstractHttpConfigurer::disable); // Form Login disable
        http.httpBasic(AbstractHttpConfigurer::disable); // 기본 basic 인증 방식 disable

        http.authorizeHttpRequests((auth) -> auth // 경로에 따른 인가 작업
                .requestMatchers("/login", "/", "/register", "/h2-console/**").permitAll()
                .requestMatchers("/authentication").authenticated()
                .requestMatchers("/admin").hasRole("ADMIN")
  );
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        // 비밀번호 패스워드 검증 이전에 jwt 토큰 검증 시도(로그인 상태 확인용도 포함)
        http.addFilterBefore(customJwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        // 동일 경로에 한하여 h2-console 접속 시 iframe 허용
        http.headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        return http.build();
    }

}
