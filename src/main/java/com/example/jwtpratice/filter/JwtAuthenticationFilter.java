package com.example.jwtpratice.filter;

import com.example.jwtpratice.config.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        log.info("token =>",token);
        log.info("request=> {}",request);
        log.info("jwtTokenProvider.validateTokenExceptExpiration(token) {}",jwtTokenProvider.validateTokenExceptExpiration(token));
        log.info(" token != null => {}",token != null);
        if (token != null && jwtTokenProvider.validateTokenExceptExpiration(token)) {
            Authentication auth = jwtTokenProvider.getAuthentication(token);
            log.info("auth=>{}",auth);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        chain.doFilter(request, response);
    }
}