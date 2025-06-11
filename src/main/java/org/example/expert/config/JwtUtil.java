package org.example.expert.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.example.expert.domain.common.exception.ServerException;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final long TOKEN_TIME = 60 * 60 * 1000L; // 60분

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String createAccessToken(Long userId, String email, UserRole userRole) {
        return BEARER_PREFIX + createToken(userId, email, userRole, 60 * 60 * 1000L); // 1시간
    }

    public String createRefreshToken(Long userId, String email, UserRole userRole) {
        return BEARER_PREFIX + createToken(userId, email, userRole, 14 * 24 * 60 * 60 * 1000L); // 14일
    }

    private String createToken(Long userId, String email, UserRole userRole, long expiryMillis) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("email", email)
                .claim("userRole", userRole)
                .setExpiration(new Date(now.getTime() + expiryMillis))
                .setIssuedAt(now)
                .signWith(key, signatureAlgorithm)
                .compact();
    }


    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        throw new ServerException("Not Found Token");
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
