package com.mukesh.inkLine.util;

import com.mukesh.inkLine.entities.Users;
import com.mukesh.inkLine.exceptions.NotFoundException;
import com.mukesh.inkLine.repository.UsersRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private final String jwtSecret;
    private final Long expiration;
    private final UsersRepository usersRepository;

    JwtUtil(
            @Value("${custom.jwt.secret}")
            String jwtSecret,
            @Value("${custom.jwt.expiration}")
            Long expiration,
            UsersRepository usersRepository
    ) {
        this.jwtSecret = jwtSecret;
        this.expiration = expiration;
        this.usersRepository = usersRepository;
    }

    public String generateAccessToken(String username, String role) {
        return Jwts.builder()
                .claims()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + (expiration)))
                .and()
                .claim("role", role)
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenValid(String token) {
        String email = extractEmail(token);
        Users requestedUser = usersRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User with specified email is not found."));
        return requestedUser.getEmail().equalsIgnoreCase(email) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}
