package com.example.KekaActionService.util;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final SecretKey accessSecreteKey = Keys.hmacShaKeyFor("/nctxZrIDSnatbXRnVRaf1OO+LOAiduMDjfgYEsAXRQ=".getBytes());
    private final SecretKey refreshSecreteKey = Keys.hmacShaKeyFor("/mctxZrIDSnatbXRnVRaf1OO+LOAiduMDjfgYEsAXRQ=".getBytes());
    private final SecretKey resetPasswordKey = Keys.hmacShaKeyFor("/lctxZrIDSnatbXRnVRaf1OO+LOAiduMDjfgYEsAXRQ=".getBytes());

    @Setter
    public String type = "";

    public Claims extractAllClaims(String token) {

        SecretKey secretKey;
        if ("access".equalsIgnoreCase(type)){
            secretKey = accessSecreteKey;
        }else if ("refresh".equalsIgnoreCase(type)){
            secretKey = refreshSecreteKey;
        }else {
            secretKey = resetPasswordKey;
        }

        return Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public  <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
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
