package com.example.jwtpratice.service;

import com.example.jwtpratice.dto.MemberDetail;
import com.example.jwtpratice.dto.MemberDto;
import com.example.jwtpratice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MemberDto member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("아이디가 존재하지 않습니다."));
        List<GrantedAuthority> roles = new ArrayList<>();
       // roles.add(new SimpleGrantedAuthority(member.getRoles().toString()));

        return MemberDetail.builder()
                .username(member.getEmail())
                .password(member.getPassword())
               // .authorities(roles)
                .build();
    }
}