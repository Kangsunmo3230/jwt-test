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


    public MemberLoginRequestDto(String email, String password,Long id, String token) {
        this.Email = email;
        this.Password = password;
        this.id = id;
        this.token = token;
    }
}
