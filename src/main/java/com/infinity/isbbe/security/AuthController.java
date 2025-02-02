package com.infinity.isbbe.security;

import com.infinity.isbbe.config.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

        // JWT 토큰 생성
        String jwt = jwtTokenProvider.createToken(authentication);

        // 응답 반환
        return new LoginResponse(jwt);
    }

    @CrossOrigin(origins = "http://localhost:5173")  // CORS 허용
    @PostMapping("/user-login")
    public LoginResponse userLogin(@RequestBody LoginRequest loginRequest) {
        // 로그인 인증
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // JWT 토큰 생성
        String jwt = jwtTokenProvider.createToken(authentication);

        // 응답 반환
        return new LoginResponse(jwt);
    }
}
