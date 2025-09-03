package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import javax.crypto.SecretKey;

public class JwtUtil {
    private static final SecretKey SECRET_KEY = Keys
            .hmacShaKeyFor("my-super-secret-key-which-should-be-long".getBytes());
    private static final long EXPIRATION_MS = 1000 * 60 * 60 * 24;

    public static String generateToken(String roomId) {
        return Jwts.builder()
                .setSubject(roomId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000 + EXPIRATION_MS))
                .signWith(SECRET_KEY)
                .compact();
    }

    public static String validateTokenAndGetRoomId(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (JwtException e) {
            throw new RuntimeException("Invalid or expired token");
        }
    }
}
