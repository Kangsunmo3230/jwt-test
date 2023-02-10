package com.example.jwtpratice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberLoginRequestDto {
    private String Email;
    private String Password;


    private Long id;
    private String token;
    public MemberLoginRequestDto(Long id, String token) {
        this.id = id;
        this.token = token;
    }
}
