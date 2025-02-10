package com.infinity.isbbe.security;

import com.infinity.isbbe.config.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @CrossOrigin(origins = "http://localhost:5173")  // CORS 허용
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        // 로그인 인증
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // Authentication 객체에서 UserDetails 추출
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 사용자 정보 (username, role) 추출
        String username = userDetails.getUsername();
        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(authority -> authority.getAuthority()) // 예시: ROLE_MEMBER 또는 ROLE_ADMIN
                .orElse("ROLE_USER"); // 기본값: ROLE_USER

        // JWT 토큰 생성
        String jwt = jwtTokenProvider.createToken(userDetails, username, role);

        // 응답 반환 (JWT와 role 포함)
        return new LoginResponse(jwt, role);
    }

    @CrossOrigin(origins = "http://localhost:5173")  // CORS 허용
    @PostMapping("/user-login")
    public LoginResponse userLogin(@RequestBody LoginRequest loginRequest) {
        // 로그인 인증
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // Authentication 객체에서 UserDetails 추출
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 사용자 정보 (username, role) 추출
        String username = userDetails.getUsername();
        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(authority -> authority.getAuthority()) // 예시: ROLE_MEMBER 또는 ROLE_ADMIN
                .orElse("ROLE_USER"); // 기본값: ROLE_USER

        // JWT 토큰 생성
        String jwt = jwtTokenProvider.createToken(userDetails, username, role);

        // 응답 반환 (JWT와 role 포함)
        return new LoginResponse(jwt, role);
    }
}

