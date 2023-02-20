package com.example.jwtpratice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberLoginRequestDto {

    private Long id;
    private String token;
    private String Email;
    private String Password;
    private String roles;
    private String refreshToken;


    public MemberLoginRequestDto(String email, String password,Long id,String roles, String token, String refreshToken) {
        this.Email = email;
        this.Password = password;
        this.id = id;
        this.roles = roles;
        this.token = token;
        this.refreshToken = refreshToken;
    }
}
