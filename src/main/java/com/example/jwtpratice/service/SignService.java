package com.example.jwtpratice.service;


import com.example.jwtpratice.config.JwtTokenProvider;
import com.example.jwtpratice.dto.MemberDto;
import com.example.jwtpratice.dto.MemberLoginRequestDto;
import com.example.jwtpratice.dto.MemberRegisterRequestDto;
import com.example.jwtpratice.expection.LoginFailureException;
import com.example.jwtpratice.expection.MemberEmailAlreadyExistException;
import com.example.jwtpratice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
                        .build());
        log.info("user => {}",user);
        return new MemberRegisterRequestDto(user.getId(), user.getEmail());
    }


    public void validateDuplicated(String email){
        if(memberRepository.findByEmail(email).isPresent())
            throw new MemberEmailAlreadyExistException();
    }

    //토큰 발급 구간
    public MemberLoginRequestDto loginMember(MemberLoginRequestDto requestDto) {
        MemberDto member = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(LoginFailureException::new);
        log.info("member => {}",member);
        log.info("requestDto => {}",requestDto);
        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())){
            throw new LoginFailureException();
        }
            return new MemberLoginRequestDto(requestDto.getEmail(), requestDto.getPassword(),member.getId(), jwtTokenProvider.createToken(requestDto.getEmail()));
    }
}
