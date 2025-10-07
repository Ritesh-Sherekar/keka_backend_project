package com.example.KekaActionService.security;

import com.example.KekaActionService.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        String token = "";

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            doFilter(request, response, filterChain);
            return;
        } else {
            token = authorizationHeader.substring(7);
        }

        jwtUtil.setType("Access");
        String userId = jwtUtil.extractUsername(token);

        Claims claims = jwtUtil.extractAllClaims(token);
        List<String> roles = (List<String>) claims.get("role");

        Set<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            boolean isTokenValid = jwtUtil.validateToken(token);

            if (isTokenValid) {
                Authentication authToken = new UsernamePasswordAuthenticationToken(userId, token, authorities);
                SecurityContextHolder.getContext().setAuthentication(authToken);
                logger.info("JWT token is valid");
            }
        }
        filterChain.doFilter(request, response);
    }
}
