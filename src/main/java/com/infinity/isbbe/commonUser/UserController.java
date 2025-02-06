package com.infinity.isbbe.commonUser;

import com.infinity.isbbe.config.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final JwtTokenProvider jwtTokenProvider;

    public UserController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // 현재 로그인한 사용자 정보 반환 (adminName 또는 memberName)
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getUserInfo(@RequestHeader("Authorization") String token) {
        // JWT 토큰에서 username을 추출
        String username = jwtTokenProvider.getUsernameFromToken(token.substring(7));  // Bearer 토큰 처리

        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Unauthorized"));
        }

        // 사용자 정보 (adminName, memberName) 반환
        return ResponseEntity.ok(Map.of("username", username));
    }

    // 로그인된 사용자가 Admin인지 Member인지 확인하고 역할별 처리
    @GetMapping("/role")
    public ResponseEntity<Map<String, Object>> getUserRole(@RequestHeader("Authorization") String token) {
        // JWT 토큰에서 role을 추출
        String role = jwtTokenProvider.getRoleFromToken(token.substring(7));  // Bearer 토큰 처리
        String username = jwtTokenProvider.getUsernameFromToken(token.substring(7));

        if (username == null || role == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Unauthorized"));
        }

        // 역할과 사용자 정보를 반환
        return ResponseEntity.ok(Map.of("role", role, "username", username));
    }
}
