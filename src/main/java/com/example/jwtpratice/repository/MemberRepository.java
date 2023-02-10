package com.example.jwtpratice.repository;

import com.example.jwtpratice.dto.MemberDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberDto, Long> {
    Optional<MemberDto> findByEmail(String email);

}
