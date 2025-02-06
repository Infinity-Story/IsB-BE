package com.infinity.isbbe.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);  // logger 필드 추가
    private final JwtConfig jwtConfig;
    private final Key secretKey;
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1시간

    @Autowired
    public JwtTokenProvider(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
        this.secretKey = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());
    }

    public String generateToken(UserDetails userDetails, String username, String role) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())  // 사용자 ID (email or username)
                .claim("username", username)  // adminName 또는 memberName
                .claim("role", role)  // 역할 (role) 추가
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, secretKey)  // secretKey 사용
                .compact();
    }

    public String createToken(UserDetails userDetails, String username, String role) {
        return generateToken(userDetails, username, role);
    }

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    // ✅ JWT에서 모든 Claims(정보)를 추출하는 메서드 추가
    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)  // Secret key를 이용하여 검증
                    .build()
                    .parseClaimsJws(token);  // 토큰을 파싱하여 유효성 검사
            return true;  // 토큰이 유효하면 true
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("JWT validation failed: " + e.getMessage());
            return false;  // 유효하지 않으면 false
        }
    }

    public String getRoleFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return (String) claims.get("role");  // JWT 토큰에서 role을 추출
    }

    public static String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof String) {
            return authentication.getPrincipal().toString();  // adminName 또는 memberName 반환
        }
        return null;
    }
}
