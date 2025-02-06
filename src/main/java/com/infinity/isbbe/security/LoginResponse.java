package com.infinity.isbbe.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String token;
    private String role;

    // 생성자: token과 role을 받아서 응답 객체를 생성
    public LoginResponse(String token, String role) {
        this.token = token;
        this.role = role;
    }
}
