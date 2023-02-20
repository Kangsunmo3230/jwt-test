package com.example.jwtpratice.service;


import com.example.jwtpratice.config.JwtTokenProvider;
import com.example.jwtpratice.dto.*;
import com.example.jwtpratice.expection.InvalidRefreshTokenException;
import com.example.jwtpratice.expection.LoginFailureException;
import com.example.jwtpratice.expection.MemberEmailAlreadyExistException;
import com.example.jwtpratice.expection.MemberNotFoundException;
import com.example.jwtpratice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SignService  {

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;


//
    @Transactional
    public MemberRegisterRequestDto registerMember(MemberRegisterRequestDto requestDto) {
        validateDuplicated(requestDto.getEmail()); //중복검사 시작
        MemberDto user = memberRepository.save(
                MemberDto.builder()
                        .email(requestDto.getEmail())
                        .password(passwordEncoder.encode(requestDto.getPassword()))
                        .refreshToken(null)
                        .build());
        return new MemberRegisterRequestDto(user.getId(), user.getEmail());
    }


    public void validateDuplicated(String email){
        if(memberRepository.findByEmail(email).isPresent())
            throw new MemberEmailAlreadyExistException();
    }

    //토큰 발급 구간
    @Transactional
    public MemberLoginRequestDto loginMember(MemberLoginRequestDto requestDto) {
        MemberDto member = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(LoginFailureException::new);

        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())){
            throw new LoginFailureException();
        }
        member.updateRefreshToken(jwtTokenProvider.createRefreshToken());
            return new MemberLoginRequestDto(requestDto.getEmail(), requestDto.getPassword(),member.getId(),member.getRoles(), jwtTokenProvider.createToken(requestDto.getEmail()),member.getRefreshToken());
    }
    /**
     * 토큰 재발행
     * @param requestDto
     * @return
     */

    @Transactional
    public TokenResponseDto reIssue(TokenRequestDto requestDto) {
        //refreshToken이 만료되었다면
        if (!jwtTokenProvider.validateTokenExpiration(requestDto.getRefreshToken()))
            throw new InvalidRefreshTokenException();
        MemberDto member  = findMemberByToken(requestDto);
        //refreshToken이 만료되었다면
        if (!member.getRefreshToken().equals(requestDto.getRefreshToken()))
            throw new InvalidRefreshTokenException();

        String accessToken = jwtTokenProvider.createToken(member.getEmail());
        String refreshToken = jwtTokenProvider.createRefreshToken();
        member.updateRefreshToken(refreshToken);
        return  new TokenResponseDto(accessToken,refreshToken);

    }
    public MemberDto findMemberByToken(TokenRequestDto requestDto){
        Authentication auth = jwtTokenProvider.getAuthentication(requestDto.getAccessToken());
        UserDetails userDetails =(UserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();
        return memberRepository.findByEmail(username).orElseThrow(MemberNotFoundException::new);
    }
}
