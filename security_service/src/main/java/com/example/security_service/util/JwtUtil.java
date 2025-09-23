package com.example.security_service.util;


import com.example.security_service.entity.Users;
import com.example.security_service.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Autowired
    private UserRepository userRepo;

    private final SecretKey accessSecreteKey = Keys.hmacShaKeyFor("/nctxZrIDSnatbXRnVRaf1OO+LOAiduMDjfgYEsAXRQ=".getBytes());
    private final SecretKey refreshSecreteKey = Keys.hmacShaKeyFor("/mctxZrIDSnatbXRnVRaf1OO+LOAiduMDjfgYEsAXRQ=".getBytes());

    @Setter
    public String type = "";

    public String generateJwtAccessToken(String userName) {

        Users users = userRepo.findByUserName(userName).orElseThrow(() -> new RuntimeException("User not found"));
        List<String> roles = users.getRoles().stream().map(role -> role.getRole()).toList();

        Map<String, Object> claims = new HashMap<>();
        claims.put("iss", "E-commerce app");
        claims.put("role", roles);

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 *24))
                .and()
                .signWith(accessSecreteKey)
                .compact();
    }

    public String generateJwtRefreshToken(String userName) {

        Users users = userRepo.findByUserName(userName).orElseThrow(() -> new RuntimeException("User not found"));
        List<String> roles = users.getRoles().stream().map(role -> role.getRole()).toList();

        Map<String, Object> claims = new HashMap<>();
        claims.put("iss", "Java App");
        claims.put("role", roles);

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30))
                .and()
                .signWith(refreshSecreteKey)
                .compact();
    }

    private Claims extractAllClaims(String token) {

        SecretKey secretKey;
        if ("access".equalsIgnoreCase(type)){
            secretKey = accessSecreteKey;
        }else {
            secretKey = refreshSecreteKey;
        }

        return Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public boolean validateToken(String token) {

        final String username = extractUsername(token);
        return (username != null && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractExpireDate(token).before(new Date());
    }

    public Date extractExpireDate(String token) {
        return extractClaims(token, Claims::getExpiration);
    }
}
