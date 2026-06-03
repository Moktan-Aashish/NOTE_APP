package com.note.backend.core.security;

import com.note.backend.core.exception.custom.auth.SecretKeyInitializationException;
import com.note.backend.core.exception.custom.auth.TokenExpiredException;
import com.note.backend.core.exception.custom.auth.TokenInvalidException;
import com.note.backend.core.exception.custom.auth.TokenMissingException;
import com.note.backend.domain.auth.dto.UserTokenPayload;
import com.note.backend.domain.auth.enums.Role;
import com.note.backend.domain.auth.enums.Token;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.token.access.expire.time}")
    private Long accessTokenExpireTime;

    @Value("${jwt.token.refresh.expire.time}")
    private Long refreshTokenExpireTime;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        if (jwtSecret == null || jwtSecret.isBlank()) {
            throw new SecretKeyInitializationException("Secret key configured improperly");
        }

        try {
            secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        } catch (Exception ex) {
            throw new SecretKeyInitializationException("Failed to initialize secret key", ex);
        }
    }

    public Optional<String> extractToken(HttpServletRequest request) {
        return TokenExtractor.extractTokenFromHeader(request);
    }

    public String generateToken(Token tokenType, UserTokenPayload payload) {
        long expirationTime = (tokenType == Token.ACCESS_TOKEN) ? accessTokenExpireTime : refreshTokenExpireTime;

        JwtBuilder builder = Jwts.builder()
                .subject(payload.email())
                .claim("userId", payload.userId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime));

        if (tokenType == Token.ACCESS_TOKEN) {
            builder.claim("role", payload.role().name());
        }

        return builder
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        if (token == null || token.isBlank()) {
            throw new TokenMissingException("Authentication token is missing");
        }

        try {
            parseClaims(token);
            return true;
        } catch (ExpiredJwtException ex) {
            throw new TokenExpiredException("Authentication token has expired", ex);
        } catch (UnsupportedJwtException ex) {
            throw new TokenInvalidException("Unsupported authentication token", ex);
        } catch (MalformedJwtException ex) {
            throw new TokenInvalidException("Invalid authentication token format", ex);
        } catch (SecurityException ex) {
            throw new TokenInvalidException("Authentication token signature is invalid", ex);
        } catch (IllegalArgumentException ex) {
            throw new TokenInvalidException("Authentication token data is invalid", ex);
        } catch (JwtException ex) {
            throw new TokenInvalidException("Authentication token is invalid", ex);
        }
    }

    public UserTokenPayload extractUserPayload(String token) {
        Claims claims = parseClaims(token);

        String email = claims.getSubject();
        String userId = claims.get("userId", String.class);
        String roleStr = claims.get("role", String.class);

        if (email == null || email.isBlank() || userId == null || userId.isBlank()) {
            throw new TokenInvalidException("Invalid authentication token payload structure");
        }

        Role role = roleStr != null ? Role.valueOf(roleStr) : Role.USER;

        return new UserTokenPayload(userId, email, role);
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
