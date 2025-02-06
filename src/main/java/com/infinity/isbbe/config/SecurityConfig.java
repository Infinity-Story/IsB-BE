package com.infinity.isbbe.config;

import com.infinity.isbbe.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration(proxyBeanMethods = true)
public class SecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(JwtTokenFilter jwtTokenFilter, CustomUserDetailsService customUserDetailsService) {
        this.jwtTokenFilter = jwtTokenFilter;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // CSRF 비활성화
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/auth/login", "/api/auth/user-login").permitAll()  // 로그인 API는 누구나 접근 가능
                        .requestMatchers("/admin/**").hasRole("ADMIN")  // /admin/** 경로는 ADMIN 권한이 있어야 접근 가능
                        .requestMatchers("/member/**").permitAll()  // /member/create 경로는 인증 없이 접근 가능
                        .requestMatchers("/member/**").hasRole("MEMBER")  // 다른 /member/** 경로는 MEMBER 권한이 있어야 접근 가능
                        .anyRequest().authenticated()  // 나머지 경로는 인증 필요
                )
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);  // JWT 필터를 UsernamePasswordAuthenticationFilter 전에 추가

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)  // 사용자 서비스 연결
                .passwordEncoder(passwordEncoder);  // 비밀번호 인코딩 설정

        return authenticationManagerBuilder.build();
    }
}


