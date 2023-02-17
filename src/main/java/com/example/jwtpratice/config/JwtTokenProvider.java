package com.example.jwtpratice.config;


import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secretKey}")
    private String secretKey;

    private long tokenValidTime = 1000L * 60 * 30; // 30분

    private final UserDetailsService memberDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String email) {
        log.info("email ==>{}",email);
        Claims claims = Jwts.claims().setSubject(email);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        log.info("getAuthentication token=> {}",token);
        log.info("getMemberEmail(token) => {}",getMemberEmail(token));
        UserDetails userDetails = memberDetailsService.loadUserByUsername(getMemberEmail(token));
        log.info("userDetails => {}",userDetails);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


    public String getMemberEmail(String token) {
        try {
            log.info("getMemberEmail token => {}",token);
            log.info("secretKeyr => {}",secretKey);
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        } catch(ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
    }

    public String resolveToken(HttpServletRequest req) {
        log.info("req=> {}",req.getHeader("X-AUTH-TOKEN"));

        return req.getHeader("X-AUTH-TOKEN");
    }

    public boolean validateTokenExceptExpiration(String token) {
        try {
        log.info("token === validation {}",token);
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch(Exception e) {
            return false;
        }
    }
}