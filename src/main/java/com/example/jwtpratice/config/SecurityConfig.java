package com.example.jwtpratice.config;

import com.example.jwtpratice.dto.MemberDto;
import com.example.jwtpratice.expection.LoginFailureException;
import com.example.jwtpratice.expection.MemberEmailAlreadyExistException;
import com.example.jwtpratice.filter.JwtAuthenticationFilter;
import com.example.jwtpratice.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final SignService signService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() // rest api이므로 기본설정 미사용
                .csrf().disable() // rest api이므로 csrf 보안 미사용
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt로 인증하므로 세션 미사용
                .and()
                .authorizeRequests()
                .antMatchers("/sign/**").permitAll() //리소스의 접근을 인증절차 없이 허용한다.
                .antMatchers("/social/**").permitAll()
                .antMatchers("/exception/**").permitAll()
                .anyRequest().authenticated() //그외 나머지 리소스들은 무조건 인증을 완료행 접근이 가능하다.
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // jwt 필터 추가
                //addfilterBefore =>  UsernamePasswordAuthenticationFileter보다 먼저 실행된다.
    }
}